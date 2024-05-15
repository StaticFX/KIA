package de.staticred.kia.inventory

import de.staticred.kia.inventory.item.KItem

/**
 * Models the footer inside a page in a paging inventory
 * @see KPage
 * @see KPageInventory
 * @since 1.0.0
 */
interface KPageController {

    /**
     * Function used to generate the KRow which represents this controller as items
     * @param nextBtn the items used to go to the next page
     * @param previousBetn the button used to go to the previous page
     * @param placeholder placeholder item
     */
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