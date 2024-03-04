package de.staticred.kia.inventory

import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.inventory.item.KItemImpl
import org.bukkit.entity.Player

/**
 * Models a row inside a regular KInventory type
 *
 * A row consists of 9 slots which can be filled with items
 */
interface KRow {
    /**
     * The items contained in the KRow
     * Maps the Slot to the KItem inside the row
     */
    val items: Map<Int, KItem>
    val name: String

    /**
     * Executed when any item inside the row is clicked
     * @param action function executed when any item is clicked
     */
    fun onClick(action: KInventory.(player: Player, row: KRowImpl, item: KItem) -> Unit)

    /**
     * Sets the parent of this KRow
     * @param kInventory parent inventory
     * @param index sets the index of the row inside the parent inventory
     */
    fun setParent(kInventory: KInventory, index: Int)

    /**
     * Returns the index inside the inventory of this row
     * @return the current index
     */
    fun getIndex(): Int

    /**
     * Sets the index inside the parent inventory inside this row
     */
    fun setIndex(index: Int)

    /**
     * @return Map containing the slot mapping to the KItem
     */
    fun getItems(): Map<Int, KItem>

    /**
     *  Executed the onClicked event listeners for this row
     *  @param player the player who clicked the item
     *  @param kItem the item clicked
     */
    fun clicked(player: Player, kItem: KItem)

    /**
     * Sets the given item in the given slot.
     * When changing an item in a slot, the inventory will update accordingly
     * @param slot the slot
     * @param item the item
     */
    fun setItem(slot: Int, item: KItem)

    /**
     * Sets the given item in the given range.
     * When changing an item in a slot, the inventory will update accordingly
     * @param range the range
     * @param item the item
     */
    fun setItem(range: IntRange, item: KItem)

}