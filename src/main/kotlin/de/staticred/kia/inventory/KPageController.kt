package de.staticred.kia.inventory

import de.staticred.kia.inventory.item.RegisteredKItem

/**
 * Models the footer inside a page in a paging inventory
 * @see KPage
 * @see KPageInventory
 * @author Devin
 * @since 1.0.0
 */
interface KPageController {

    /**
     * Function used to generate the KRow which represents this controller as items
     * @property nextBtn the items used to go to the next page
     * @property previousBetn the button used to go to the previous page
     * @property placeholder placeholder item
     */
    var builder: (nextBtn: RegisteredKItem?, previousBtn: RegisteredKItem?, placeholder: RegisteredKItem?) -> KRow

    /**
     * Item used as a button for the user to go to the next page
     * A click listener will be attached to it when the inventory is built, to handle pagination
     */
    var nextBtn: RegisteredKItem?

    /**
     * Item used as a button for the user to go to the previous page
     * A click listener will be attached to it when the inventory is built, to handle pagination
     */
    var previousBtn: RegisteredKItem?

    /**
     * Item which can be used in the builder as a placeholder
     */
    var placeholderItem: RegisteredKItem?

    /**
     * Used to build the controller, which will then be placed on the page as a row.
     * Using the builder supplied
     * @see builder
     */
    fun build(): KRow
}