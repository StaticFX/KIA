package de.staticred.kia.inventory

import de.staticred.kia.animation.Animatable
import de.staticred.kia.animation.Animation
import net.kyori.adventure.text.Component

/**
 * Models a page inside a paging KInventory
 * @see KPageInventory
 */
interface KPage: Animatable<KPage>, InventoryContentContainer {

    /**
     * Title of the page which will be rendered, can be configured using the TitleBuilder in the Parent Inventory
     * @see KPageInventory.titleBuilder
     */
    var title: Component?

    /**
     * The header will only be rendered, if the inventory is at least 2 rows long, and has no footer, or is 3 rows long
     */
    var header: KPageController?

    /**
     * Animation played as soon as the page is opened
     */
    var openingAnimation: Animation<KPage>?

    /**
     * The footer will only be rendered, if the inventory is at least 2 rows long, and has no header, or is 3 rows long
     */
    var footer: KPageController?

    /**
     * Parent inventory if the page is set in it
     */
    var parent: KPageInventory?

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
     * Returns the given row for the index as a new object
     * Parent of the row will only be set, if the page has a parent as well
     * @param index index of the row
     * @return new KRow instance
     */
    override fun getRowFor(index: Int): KRow

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