package de.staticred.kia.inventory

import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.inventory.item.KItemImpl

/**
 * Models an inventory which support a pagination system
 *
 * Works by saving the given pages and displaying them when reached
 *
 * @see Page
 */
interface PageKInventory: KInventory {

    /**
     * Whether the inventory should loop when the last page is reached
     * If true will loop back to the first page when going to the next page on the last page
     */
    var looping: Boolean

    /**
     * Sets the current page displayed in the inventory
     * @param index page index
     */
    fun setPage(index: Int)

    /**
     * Inserts the given page into the inventory at the given index
     */
    fun insertPage(index: Int, page: Page)

    /**
     * Adds a page to the current inventory
     */
    fun addPage(page: Page)

    /**
     * Removes the given space from the inventory
     */
    fun removePage(page: Page)

    /**
     * Moves to the next page in the inventory
     * If looping is enabled will loop back to the first page when end is reached
     */
    fun nextPage()

    /**
     * Moves to the previous page in the inventory
     * If looping is enabled will loop back to the last page when end is reached
     */
    fun previousPage()

    /**
     * @return the current page displayed
     */
    fun getPage(): Page
}

/**
 * Models a page inside a paging KInventory
 * @see PageKInventory
 */
interface Page {

    /**
     * @return the content of the page
     */
    fun getContent(): List<KItem>

    /**
     * Returns the footer of the page
     * The footer will only be rendered, if the inventory is at least 2 rows long, and has no header, or is 3 rows long
     * @return the page footer
     */
    fun getFooter(): PageFooter?

    /**
     * Returns the header of the page
     * The header will only be rendered, if the inventory is at least 2 rows long, and has no footer, or is 3 rows long
     */
    fun getHeader(): PageHeader?

    /**
     * Sets the content of the inventory with the given items
     * @param items list of items
     */
    fun setContent(items: List<KItem>)

    /**
     * Sets the given item at the given slot
     * Slots are relative to the inventory page. If a header is existing, the slots will be shifted by the length of 9.
     * If a footer is existing, the maximum slots will be reduced by 9
     */
    fun setItem(slot: Int, items: List<KItem>)

    /**
     * @return whether the page has a header or not
     */
    fun hasHeader(): Boolean

    /**
     * @return whether the page has a footer or not
     */
    fun hasFooter(): Boolean
}

/**
 * Models the footer inside a page in a paging inventory
 * @see Page
 * @see PageKInventory
 */
interface PageFooter {

    /**
     *
     */
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