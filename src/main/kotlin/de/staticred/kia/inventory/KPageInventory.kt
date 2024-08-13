package de.staticred.kia.inventory

import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.inventory.kinventory.KInventory
import net.kyori.adventure.text.Component

/**
 * Models an inventory which support a pagination system
 *
 * Works by saving the given pages and displaying them when reached
 *
 * @see KPage
 * @author Devin
 * @since 1.0.0
 */
interface KPageInventory: KInventory {

    /**
     * Is the first page to display when the inventory is opened. If null then no page will be displayed
     */
    var mainPage: KPage?

    /**
     * Static pages are used for pages which should not be in the standard pagination, but serve as a static content display page.
     * So in the normal page cycle these pages won't show
     * Pages will be identified by the given identifier
     */
    var staticPages: MutableMap<String, KPage>

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
     *
     * @param slot the absolut slot
     * @param kItem the item
     */
    fun setItemOverride(slot: Int, kItem: KItem)

    /**
     * Sets the item relative to the current page, respecting the header and the footer
     * If a header is set, the rows will be shifted by the header row.
     * If a footer is set, the slots inside the last row are not accessible
     *
     * If you want to set these slots anyway, use
     * @see setItemOverride
     *
     * @throws IllegalStateException when the slot is bigger than the size or in the footer
     * @param slot the slot in the page.
     * @param value the item to set
     */
    override fun setItem(slot: Int, value: KItem)

    /**
     * Inserts the given page into the inventory at the given index
     */
    fun insertPage(index: Int, page: KPage)

    /**
     * Adds a page to the current inventory
     */
    fun addPage(page: KPage)

    /**
     * Adds a page to the current inventory
     */
    fun addPage(init: KPage.() -> Unit): KPage

    /**
     * Adds a static page to this inventory
     * @see staticPages
     */
    fun addStaticPage(identifier: String, page: KPage)

    /**
     * Adds a static page to this inventory
     * @see staticPages
     */
    fun addStaticPage(identifier: String, init: KPage.() -> Unit): KPage

    /**
     * Set the current page to the given static page
     * The current index will be saved
     * To get back to the indexed pages use
     * @see setCurrentIndexedPage
     */
    fun setStaticPage(identifier: String)

    /**
     * Sets the page with the current index
     */
    fun setCurrentIndexedPage()

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


    override fun isAnimating(): Boolean

    /**
     * Main page builder
     * @see mainPage
     * @param init builder function for a KPage
     * @return the main page
     */
    fun mainPage(init: KPage.() -> Unit): KPage

    /**
     * Build a title for the inventory based on the given builder
     * @see titleBuilder
     * @return The title component, null if none was built
     */
    fun buildTitle(): Component?

    /**
     * Util function to notify this inventory to rebuild the current page.
     * Used when a page is changed, and needs to be updated in the parent inventory.
     * @param kPage the page which has been updated
     */
    fun notifyParent(kPage: KPage)

}