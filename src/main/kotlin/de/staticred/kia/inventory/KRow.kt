package de.staticred.kia.inventory

import de.staticred.kia.inventory.impl.KRowImpl
import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.util.ShiftDirection
import org.bukkit.entity.Player

/**
 * Models a row inside a regular KInventory type
 *
 * A row consists of 9 slots which can be filled with items
 *
 * @author Devin
 * @since 1.0.0
 */
interface KRow {
    /**
     * The items contained in the KRow
     * Maps the Slot to the KItem inside the row
     */
    val items: Map<Int, KItem>

    /**
     * Name of the row
     */
    @Deprecated("Serves no use")
    val name: String

    /**
     * The parent container of this row.
     * This should always be set by the parent inventory or page, and will be set automatically when using the builders!
     */
    var parent: InventoryContentContainer?

    /**
     * Index of the row inside the parent container
     * @see parent
     */
    var index: Int

    /**
     * Executed when any item inside the row is clicked
     * @param action function executed when any item is clicked
     */
    fun onClick(action: InventoryContentContainer.(player: Player, row: KRowImpl, item: KItem) -> Unit)

    /**
     *  Executed the onClicked event listeners for this row
     *  @param player the player who clicked the item
     *  @param kItem the item clicked
     */
    fun clicked(player: Player, kItem: KItem)

    /**
     * Sets the given item in the given slot.
     * When changing an item in a slot, the inventory will update accordingly
     *
     * The slot must not be actually inside the inventory, so a slot of -1 would result in an item left to the slot 0.
     * When now shifting to the right, the item from slot 0 will come
     * @see shift
     *
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

    /**
     * Shifts the items inside the row to the given direction.
     * Because items can also be set at a negative slot or slot larger than 9, the items next to it will come.
     * If wrap is enabled, it will wrap the last items back to the beginning.
     * @param direction of the shift
     * @param amount how many times
     * @param wrap whether to wrap the items or not
     */
    fun shift(direction: ShiftDirection, amount: Int, wrap: Boolean)

}