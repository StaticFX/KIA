package de.staticred.kia.inventory

import de.staticred.kia.animation.Animatable
import de.staticred.kia.animation.Animation
import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.util.Identifiable
import net.kyori.adventure.text.Component
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import java.util.*

/**
 * Models a helper class to create bukkit inventories with ease, and provide helper functions to minimize boilerplate code
 *
 *
 * Bukkit inventories are therefore split into their global slots (0 - size)
 * and their rows (size / 9) - given the inventory type supports rows
 *
 * An inventory can hold rows, so rows can be reused at any point in the inventory.
 *
 * @since 1.0
 */
interface KInventory: Identifiable<UUID>, Animatable<KInventory> {

    /**
     * Maps the slot of the item in the inventory to the actual item
     */
    var content: MutableMap<Int, KItem>

    /**
     * Animation which will be played when the inventory is opened
     * @see opened
     */
    var openingAnimation: Animation<KInventory>?

    /**
     * Title of the inventory rendered in the bukkit inventory
     */
    var title: Component?

    /**
     * Views which currently look at the inventory
     */
    var views: MutableList<InventoryView>

    /**
     * Client side inventory instances
     */
    var inventories: MutableList<Inventory>

    /**
     * Sets the given item in the given slot
     * @param slot the global slot in the inventory
     * @param item the item to set
     */
    fun setItem(slot: Int, item: KItem)

    /**
     * Sets the given item in the row in the slot
     * Will translate the row into the slot, so row 1 slot 1 will be translated to slot 10
     * @param row inventory row
     * @param slot inventory slot
     * @param item the item
     */
    fun setItem(row: Int, slot: Int, item: KItem)

    /**
     * sets the given row in the inventory
     * @warning only when the inventory type supports rows!
     *
     * @param rowIndex the index of the row
     * @param row the row
     */
    fun setRow(rowIndex: Int, row: KRow)

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
     * Saves the row to the inventory by the rows name
     * @param row to save
     */
    fun saveRow(row: KRow)

    /**
     * Clears the inventory from all items inside it
     */
    fun clearInventory()

    /**
     * Gets the row saved with the given name
     * @return the row, or null if not found
     */
    fun getRow(name: String): KRow?

    /**
     * Sets whether the items can be moved while animating or not
     * @param value true if can be moved, false otherwise
     */
    fun setItemsClickableWhileAnimating(value: Boolean)

    /**
     * @return whether items can be clicked while animating or not
     */
    fun itemsClickableWhileAnimating(): Boolean

    /**
     * Executed when the inventory is opened
     * To catch this the bukkit event is used
     *
     * @param action executed when opened
     */
    fun onOpen(action: KInventory.() -> Unit)

    /**
     * Executed when the inventory is closed
     * To catch this the bukkit event is used
     *
     * @param action executed when closed
     */
    fun onClose(action: KInventory.() -> Unit)

    /**
     * Returns the KInventoryHolder of this inventory
     * @return the holder
     */
    fun getKHolder(): KInventoryHolder

    /**
     * Constructs the bukkit inventory from this KInventory with the current set content
     * @return the bukkit inventory
     */
    fun toBukkitInventory(): Inventory

    /**
     * Returns all the items in the inventory which have been set
     * @warning items which have been added from players are not in this map
     * @return a map where the slot is mapped to the item
     */
    fun getItems(): Map<Int, KItem>

    /**
     * @return the first KRow which contains the given KItem
     */
    fun getRowForItem(item: KItem): KRow?

    /**
     * @return the slot for the given item, -1 if item is not set yet
     */
    fun getSlotForItem(item: KItem): Int

    /**
     * Checks whether the inventory is private.
     * Private indicated that only one player at a time can open the same reference to a KInventory
     * @return boolean
     */
    fun isPrivate(): Boolean

    /**
     * @return if the inventory is currently opened or closed
     */
    fun isOpened(): Boolean

    /**
     * Sets the inventory to opened
     * Should be called when the inventory has been opened.
     */
    fun opened()

    /**
     * Sets the inventory to closed
     * Should be called when the inventory has been closed.
     */
    fun closed()

    /**
     * Checks whether the given inventory is the same inventory
     * @param inventory the bukkit inventory
     */
    fun isEqual(inventory: Inventory): Boolean
}