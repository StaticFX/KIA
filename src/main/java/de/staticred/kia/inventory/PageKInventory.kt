package de.staticred.kia.inventory

import de.staticred.kia.inventory.item.KItem

interface PageKInventory {

    fun setPage(index: Int)

    fun addPage(page: Page)
    fun removePage()

    fun nextPage()
    fun previousPage()

    fun getPage(): Page

}

interface Page {

    fun getContent(): List<KItem>
    fun getFooter(): PageFooter
    fun getHeader(): PageHeader
    fun setContent(items: List<KItem>)
    fun setItem(slot: Int, items: List<KItem>)
    fun hasHeader(): Boolean
    fun hasFooter(): Boolean

}

interface PageFooter {

    fun getNextBtnItem(): KItem?
    fun getPlaceHolderItem(): KItem?
    fun getPreviousItem(): KItem?
    fun asRow(): KRow

}

interface PageHeader {

    fun getNextBtnItem(): KItem?
    fun getPlaceHolderItem(): KItem?
    fun getPreviousItem(): KItem?
    fun asRow(): KRow
}