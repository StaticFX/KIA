package de.staticred.kia.inventory

import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.inventory.kinventory.ContainerEvents
import org.bukkit.entity.Player

/**
 * Models a content container for inventory items
 *
 * @author Devin
 * @since 1.0.0
 */
abstract class InventoryContentContainer<T>: ContainerEvents<T>() {

    /**
     * Content of the container
     */
    abstract val content: MutableMap<Int, KItem>

    /**
     * Sets the given item at the given position
     * @param slot the slot
     * @param value the item
     */
    abstract fun setItem(slot: Int, value: KItem)

    /**
     * Sets the given row at the given index
     * @param index index of the row
     * @param row the row
     */
    abstract fun setRow(index: Int, row: KRow)

    /**
     * Sets the given item relative to the slot of the given row
     * @param row the row
     * @param slot the slot of the row
     * @param item the item
     */
    abstract fun setItem(row: Int, slot: Int, item: KItem)

    /**
     * Gets the row for the given index
     * This will create a new row for the returned value
     * @return the row
     */
    abstract fun getRowFor(index: Int): KRow

    /**
     * Swaps two rows in an inventory
     * @param row the first row
     * @param otherRow the row to swap with
     */
    abstract fun swapRow(row: KRow, otherRow: KRow)

    /**
     * Swaps two rows based on their index
     * @param index of the first row
     * @param otherIndex of the row to swap with
     */
    abstract fun swapRow(index: Int, otherIndex: Int)

    /**
     * Clears the inventory from all items inside it
     */
    abstract fun clearInventory()
}