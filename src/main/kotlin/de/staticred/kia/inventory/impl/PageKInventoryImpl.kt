package de.staticred.kia.inventory.impl

import de.staticred.kia.inventory.BaseKInventory
import de.staticred.kia.inventory.KPage
import de.staticred.kia.inventory.PageKInventory
import org.bukkit.inventory.InventoryHolder

class PageKInventoryImpl(val mainPage: KPage, owner: InventoryHolder?, override var looping: Boolean): BaseKInventory(owner), PageKInventory {

    var private: Boolean = false

    private val pages = mutableListOf(mainPage)
    private var currentPage = mainPage
    private var pageIndex = 0

    override fun isPrivate(): Boolean {
        return private
    }

    override fun setPage(index: Int) {
        if (index >= pages.size) throw IllegalArgumentException("Index can't be bigger than pages in the inventory!")
        pageIndex = index
        currentPage = pages[pageIndex]

        buildPage()
    }

    private fun buildPage() {
        buildHeader()
        buildFooter()

        val startSlot = if (currentPage.hasHeader()) 9 else 0
        val endSlot = if (currentPage.hasFooter()) size - 10 else size - 1

        val content = currentPage.content

        for ((slot, index) in (startSlot..endSlot).withIndex()) {
            content[index]?.let { setItem(slot, it) }
        }
    }

    private fun buildHeader() {
        if (currentPage.hasHeader()) {
            val header = currentPage.header ?: error("Page header is null")
            setRow(0, header.asRow())
        }
    }

    private fun buildFooter() {
        if (currentPage.hasFooter()) {
            val footer = currentPage.footer ?: error("Page header is null")

            val lastRow = size / 9

            setRow(lastRow - 1, footer.asRow())
        }
    }

    override fun insertPage(index: Int, page: KPage) {
        if (index > pages.size) throw IllegalArgumentException("Index can't exceed page sites")
        pages.add(index, page)
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

    override fun getPage(): KPage {
        return currentPage
    }
}