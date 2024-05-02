package de.staticred.kia.inventory

import de.staticred.kia.inventory.item.KItem
import net.kyori.adventure.text.Component

/**
 * Models an inventory which support a pagination system
 *
 * Works by saving the given pages and displaying them when reached
 *
 * @see KPage
 */
interface KPageInventory: KInventory {

    /**
     * Is the first page to display when the inventory is opened. If null then no page will be displayed
     */
    var mainPage: KPage?

    /**
     * Whether the inventory should loop when the last page is reached
     * If true will loop back to the first page when going to the next page on the last page
     */
    var looping: Boolean

    /**
     * Whether the current page index should be saved when the inventory is closed
     */
    var savePageWhenClosed: Boolean

    /**
     * List of the pages of this inventory. Index of the item is also the index of the page
     */
    var pages: MutableList<KPage>

    /**
     * If supplied this function is used to build the title for the inventory. When the page switches, this function will be called with the current page
     */
    var titleBuilder: ((KPage, KPageInventory) -> Component)?

    /**
     * Formatted title given by the titleBuilder
     * @see titleBuilder
     */
    var formattedTitle: Component?

    /**
     * Sets the current page displayed in the inventory
     * @param index page index
     */
    fun setPage(index: Int)

    /**
     * Sets the item to the absolut slot in the inventory, overriding the already existing item.
     * This function can be used to override header and footer of the page
     * @param slot the absolut slot
     * @param kItem the item
     */
    fun setItemOverride(slot: Int, kItem: KItem)

    /**
     * Sets the item relative to the current page, respecting the header and the footer
     * @throws IllegalStateException when the slot is bigger than the size or in the footer
     * @param slot the slot in the page.
     * @param item the item to set
     */
    override fun setItem(slot: Int, item: KItem)

    /**
     * Inserts the given page into the inventory at the given index
     */
    fun insertPage(index: Int, page: KPage)

    /**
     * Adds a page to the current inventory
     */
    fun addPage(page: KPage)


    fun addPage(init: KPage.() -> Unit): KPage

    /**
     * Removes the given space from the inventory
     */
    fun removePage(page: KPage)

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
    fun getPage(): KPage?


    /**
     * Main page builder
     * @see mainPage
     */
    fun mainPage(init: KPage.() -> Unit): KPage

    /**
     * Build a title for the inventory based on the given builder
     * @see titleBuilder
     */
    fun buildTitle(): Component?
}

/**
 * Models a page inside a paging KInventory
 * @see KPageInventory
 */
interface KPage {

    /**
     * Title of the page which will be rendered, can be configured using the TitleBuilder in the Parent Inventory
     * @see KPageInventory.titleBuilder
     */
    var title: Component?

    /**
     * The header will only be rendered, if the inventory is at least 2 rows long, and has no footer, or is 3 rows long
     */
    var header: PageController?

    /**
     * The footer will only be rendered, if the inventory is at least 2 rows long, and has no header, or is 3 rows long
     */
    var footer: PageController?

    /**
     * Content of this page, where the slot is mapped to the item
     * Slots are always relative to the page. So slot 0 is depending on if the page has a header or not
     */
    var content: MutableMap<Int, KItem>

    /**
     * Sets the given item at the given slot
     * Slots are relative to the inventory page. If a header is existing, the slots will be shifted by the length of 9.
     * If a footer is existing, the maximum slots will be reduced by 9
     */
    fun setItem(slot: Int, item: KItem)

    /**
     * @return whether the page has a header or not
     */
    fun hasHeader(): Boolean

    /**
     * @return whether the page has a footer or not
     */
    fun hasFooter(): Boolean

    /**
     * Called by the parent inventory, when the page is opened
     * @param inventory inventory which opened the page
     */
    fun opened(inventory: KPageInventory)


    /**
     * Called by the parent inventory, when the page is closed
     * @param inventory inventory which opened the page
     */
    fun closed(inventory: KPageInventory)

    /**
     * Hook called when the page is opened, either when the page is clicked to, or it's the first page of the parent inventory
     * @param action hook when the page is opened
     */
    fun onOpened(action: KPage.(parent: KPageInventory) -> Unit)

    /**
     * Hook called when the page is closed, either when the page is clicked away, or the inventory is closed when the page is active
     * @param action hook when the page is opened
     */
    fun onClosed(action: KPage.(parent: KPageInventory) -> Unit)
}

/**
 * Models the footer inside a page in a paging inventory
 * @see KPage
 * @see KPageInventory
 */
interface PageController {

    var builder: (nextBtn: KItem?, previousBtn: KItem?, placeholder: KItem?) -> KRow

    /**
     * Item used as a button for the user to go to the next page
     * A click listener will be attached to it when the inventory is built, to handle pagination
     */
    var nextBtn: KItem?

    /**
     * Item used as a button for the user to go to the previous page
     * A click listener will be attached to it when the inventory is built, to handle pagination
     */
    var previousBtn: KItem?

    /**
     * Item which can be used in the builder as a placeholder
     */
    var placeholderItem: KItem?

    /**
     * Used to build the controller, which will then be placed on the page as a row.
     * Using the builder supplied
     * @see builder
     */
    fun build(): KRow
}