package de.staticred.kia.inventory.impl

import de.staticred.kia.animation.Animation
import de.staticred.kia.inventory.*
import de.staticred.kia.inventory.item.KItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryHolder

class KPageInventoryImpl(owner: KInventoryHolder, size: Int = 3*9, override var looping: Boolean,
                         title: Component?
): BaseKInventoryImpl(owner, size, InventoryType.CHEST, title), KPageInventory {

    var private: Boolean = false
    override var savePageWhenClosed: Boolean = false
    override var mainPage: KPage? = null

    override var pages: MutableList<KPage> = if (mainPage == null) mutableListOf() else mutableListOf(mainPage!!)
    override var staticPages: MutableMap<String, KPage> = mutableMapOf()

    private var currentPage: KPage? = mainPage
    private var pageIndex = 0
    private var lastPage = 0

    override var currentAnimation: Animation<KInventory>? = null
    override var titleBuilder: ((KPage, KPageInventory) -> Component)? = null

    override fun isPrivate(): Boolean {
        return private
    }

    override var title: Component? = title

    override var formattedTitle: Component? = null
        get() {
            if (titleBuilder != null) return buildTitle()
            return field
        }


    override fun setPage(index: Int) {
        if (index >= pages.size) throw IllegalArgumentException("Index is out of bounds for the pages. Max size: ${pages.size} given index: $index")
        setPageSave(index)
    }

    private fun setPageSave(index: Int) {
        if (index >= pages.size) return

        currentPage?.closed(this)
        pageIndex = index

        currentPage = pages[index]

        val title = buildTitle()
        title?.let { views.forEach { view -> view.title = LegacyComponentSerializer.legacySection().serialize(it) } }

        buildPage()
    }

    private fun buildPage(playAnimation: Boolean = true) {
        clearInventory()
        val page = currentPage ?: return

        page.parent = this

        buildHeader(page)
        buildFooter(page)

        val startSlot = if (page.hasHeader()) 9 else 0

        val endSlot = if (page.hasFooter()) size - 10 else size - 1

        val content = page.content

        for ((index, slot) in (startSlot..endSlot).withIndex()) {
            content[index]?.let { super.setItem(slot, it) }
        }

        if (playAnimation)
            page.opened(this)
    }

    override fun setItem(row: Int, slot: Int, item: KItem) {
        val page = currentPage ?: return super.setItem(row, slot, item)

        val rowOffset = if (page.hasHeader()) 1 else 0

        if (row > size / 9) error("Row can't be bigger than the existing rows")
        if (page.hasFooter() && row > size / 9 - 1) error("Row can't be bigger than the existing rows")

        super.setItem(row + rowOffset, slot , item)

        buildPage(false)
    }

    override fun setItem(slot: Int, item: KItem) {
        currentPage?.setItem(slot, item)

        val page = currentPage ?: return

        val slotOffset = if (page.hasHeader()) 9 else 0

        if (page.hasFooter() && slot > size - 10) error("Cant set item in slot $slot because the page has a footer. Use setItemOverride() to override the footer")

        super.setItem(slot + slotOffset, item)

        buildPage(false)
    }

    override fun setRow(index: Int, row: KRow) {
        super.setRow(index, row)
        row.parent = this

        buildPage(false)
    }

    override fun isAnimating(): Boolean {
        if (currentAnimation?.isRunning() == true) return true
        if (currentPage?.isAnimating() == true) return true
        return false
    }

    override fun setItemOverride(slot: Int, kItem: KItem) {
        super.setItem(slot, kItem)
    }

    override fun setCurrentIndexedPage() {
        buildPage()
    }

    override fun setStaticPage(identifier: String) {
        val page = staticPages[identifier] ?: error("Given static page $identifier not found")
        currentPage = page
        buildPage()
    }

    private fun buildHeader(page: KPage) {
        if (page.hasHeader()) {
            val header = page.header ?: error("Page header is null")
            super.setRow(0, header.build())
        }
    }

    private fun buildFooter(page: KPage) {
        if (page.hasFooter()) {
            val footer = page.footer ?: error("Page header is null")
            val lastRow = size / 9

            super.setRow(lastRow - 1, footer.build())
        }
    }

    override fun insertPage(index: Int, page: KPage) {
        if (index > pages.size) throw IllegalArgumentException("Index can't exceed page sites")
        pages[index] = page
    }

    override fun addPage(page: KPage) {
        page.parent = this
        pages += page
    }

    override fun addPage(init: KPage.() -> Unit): KPage {
        val page = KPageImpl(null).apply(init)
        addPage(page)
        return page
    }

    override fun addStaticPage(identifier: String, page: KPage) {
        page.parent = this
        staticPages[identifier] = page
    }

    override fun addStaticPage(identifier: String, init: KPage.() -> Unit): KPage {
        val page = KPageImpl(Component.empty()).apply(init)
        addStaticPage(identifier, page)
        return page
    }

    override fun removePage(page: KPage) {
        pages.remove(page)
    }

    override fun nextPage() {
        pageIndex += 1

        if (pageIndex >= pages.size)
            if (looping) pageIndex = 0 else return

        setPage(pageIndex)
    }

    override fun previousPage() {
        if (pageIndex - 1 < 0) {
            if (looping) {
                pageIndex = pages.size - 1
            }
            return
        }

        pageIndex -= 1
        setPage(pageIndex)
    }

    override fun getPage(): KPage? {
        return currentPage
    }

    override fun getRowFor(index: Int): KRow {
        val row = super.getRowFor(index)
        row.parent = this
        return row
    }

    override fun mainPage(init: KPage.() -> Unit): KPage {
        val page = KPageImpl(Component.empty()).apply(init)
        addPage(page)
        this.mainPage = page
        if (this.currentPage == null) this.currentPage = mainPage
        return page
    }

    override fun buildTitle(): Component? {
        val page = currentPage ?: return null
        if (titleBuilder != null) {
            return titleBuilder?.let { it(page, this) }
        }
        return title
    }

    override fun closed() {
        lastPage = pageIndex
        currentPage?.closed(this)
        super.closed()
    }

    override fun opened() {
        buildPage()

        if (savePageWhenClosed) {
            setPageSave(lastPage)
        }

        super.opened()
    }

    override fun notifyParent(kPage: KPage) {
        if (currentPage == kPage) {
            buildPage(false)
        }
    }
}