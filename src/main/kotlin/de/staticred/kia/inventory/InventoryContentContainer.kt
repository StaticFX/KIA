package de.staticred.kia.inventory

import de.staticred.kia.inventory.item.KItem

/**
 * Models a content container for inventory items
 *
 * @author Devin
 * @since 1.0.0
 */
interface InventoryContentContainer {

    /**
     * Content of the container
     */
    val content: MutableMap<Int, KItem>

    /**
     * Sets the given item at the given position
     * @param slot the slot
     * @param value the item
     */
    fun setItem(slot: Int, value: KItem)

    /**
     * Sets the given row at the given index
     * @param index index of the row
     * @param row the row
     */
    fun setRow(index: Int, row: KRow)

    /**
     * Sets the given item relative to the slot of the given row
     * @param row the row
     * @param slot the slot of the row
     * @param item the item
     */
    fun setItem(row: Int, slot: Int, item: KItem)

    /**
     * Gets the row for the given index
     * This will create a new row for the returned value
     * @return the row
     */
    fun getRowFor(index: Int): KRow

    /**
     * Swaps two rows in an inventory
     * @param row the first row
     * @param otherRow the row to swap with
     */
    fun swapRow(row: KRow, otherRow: KRow)

    /**
     * Swaps two rows based on their index
     * @param index of the first row
     * @param otherIndex of the row to swap with
     */
    fun swapRow(index: Int, otherIndex: Int)


    /**
     * Clears the inventory from all items inside it
     */
    fun clearInventory()
}