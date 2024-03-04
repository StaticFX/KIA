package de.staticred.kia.inventory

import de.staticred.kia.inventory.item.KItemImpl

/**
 * Models a inventory which support a pagination system
 */
interface PageKInventory {

    /**
     * Sets the current page displayed in the inventory
     * @param index page index
     */
    fun setPage(index: Int)

    /**
     * Adds a page to the current inventory
     */
    fun addPage(page: Page)
    fun removePage()

    fun nextPage()
    fun previousPage()

    fun getPage(): Page

}

interface Page {

    fun getContent(): List<KItemImpl>
    fun getFooter(): PageFooter
    fun getHeader(): PageHeader
    fun setContent(items: List<KItemImpl>)
    fun setItem(slot: Int, items: List<KItemImpl>)
    fun hasHeader(): Boolean
    fun hasFooter(): Boolean

}

interface PageFooter {

    fun getNextBtnItem(): KItemImpl?
    fun getPlaceHolderItem(): KItemImpl?
    fun getPreviousItem(): KItemImpl?
    fun asRow(): KRowImpl

}

interface PageHeader {

    fun getNextBtnItem(): KItemImpl?
    fun getPlaceHolderItem(): KItemImpl?
    fun getPreviousItem(): KItemImpl?
    fun asRow(): KRowImpl
}