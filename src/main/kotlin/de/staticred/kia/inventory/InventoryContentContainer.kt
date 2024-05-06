package de.staticred.kia.inventory

import de.staticred.kia.inventory.item.KItem

/**
 * Models a content container for inventory items
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


    fun getRowFor()


}