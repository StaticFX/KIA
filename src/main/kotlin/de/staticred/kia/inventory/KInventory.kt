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
 * @author Devin
 * @since 1.0.0
 */
interface KInventory: Identifiable<UUID>, Animatable<KInventory>, InventoryContentContainer {
    
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
     * Sets whether the items can be clicked while the inventory is inside an animation
     *
     * When false, the onClick function on an item won't be called.
     *
     * @see Animation
     * @see KItem.onClick
     */
    var itemClickableWhileAnimating: Boolean

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