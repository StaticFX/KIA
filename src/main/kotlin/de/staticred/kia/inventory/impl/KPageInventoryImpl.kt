package de.staticred.kia.inventory.impl

import de.staticred.kia.inventory.BaseKInventory
import de.staticred.kia.inventory.KInventoryHolder
import de.staticred.kia.inventory.KPage
import de.staticred.kia.inventory.KPageInventory
import de.staticred.kia.inventory.item.KItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryHolder

class KPageInventoryImpl(owner: KInventoryHolder, override var looping: Boolean,
                         title: Component?
): BaseKInventoryImpl(owner, InventoryType.CHEST, title), KPageInventory {

    var private: Boolean = false
    override var savePageWhenClosed: Boolean = false
    override var mainPage: KPage? = null
    override var pages: MutableList<KPage> = if (mainPage == null) mutableListOf() else mutableListOf(mainPage!!)
    private var currentPage: KPage? = mainPage
    private var pageIndex = 0
    private var lastPage = 0

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
        buildPage()

        val title = buildTitle()
        title?.let { views.forEach { view -> view.title = LegacyComponentSerializer.legacySection().serialize(it) } }

        currentPage?.opened(this)
    }

    private fun buildPage() {
        val page = currentPage ?: return

        println("Building page: ${page.title}")

        buildHeader(page)
        buildFooter(page)

        val startSlot = if (page.hasHeader()) 9 else 0
        println(startSlot)

        val endSlot = if (page.hasFooter()) size - 10 else size - 1

        val content = page.content

        for ((slot, index) in (startSlot..endSlot).withIndex()) {
            content[index]?.let { super.setItem(slot, it) }
        }
    }

    override fun setItem(slot: Int, item: KItem) {
        currentPage?.setItem(slot, item)
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
        pages += page
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
        pageIndex -= 1
        if (pageIndex < 0)
            if (looping) pageIndex = pages.size - 1 else return
        setPage(pageIndex)
    }

    override fun getPage(): KPage? {
        return currentPage
    }

    override fun mainPage(init: KPage.() -> Unit): KPage {
        val page = KPageImpl(Component.empty()).apply(init)
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
}