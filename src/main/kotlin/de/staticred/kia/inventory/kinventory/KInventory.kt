package de.staticred.kia.inventory.kinventory

import de.staticred.kia.animation.Animatable
import de.staticred.kia.animation.Animation
import de.staticred.kia.inventory.InventoryContentContainer
import de.staticred.kia.inventory.KInventoryHolder
import de.staticred.kia.inventory.KRow
import de.staticred.kia.inventory.events.KEventData
import de.staticred.kia.inventory.events.OpenEvent
import de.staticred.kia.inventory.events.OpenEventData
import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.util.Identifiable
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
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
abstract class KInventory: Identifiable<UUID>, Animatable<KInventory>, InventoryContentContainer<KInventory>() {
    
    /**
     * Animation which will be played when the inventory is opened
     * @see opened
     */
    abstract var openingAnimation: Animation<KInventory>?

    /**
     * Title of the inventory rendered in the bukkit inventory
     */
    abstract var title: Component?

    /**
     * Views which currently look at the inventory
     */
    abstract var views: MutableList<InventoryView>

    /**
     * Client side inventory instances
     */
    abstract var inventories: MutableList<Inventory>

    /**
     * Sets whether the items can be clicked while the inventory is inside an animation
     *
     * When false, the onClick function on an item won't be called.
     *
     * @see Animation
     * @see de.staticred.kia.inventory.item.RegisteredKItem.onClick
     */
    abstract var itemClickableWhileAnimating: Boolean

    /**
     * Executed when the inventory is closed
     * To catch this the bukkit event is used
     *
     * @param action executed when closed
     */
    abstract fun onClose(action: KInventory.() -> Unit)

    /**
     * Returns the KInventoryHolder of this inventory
     * @return the holder
     */
    abstract fun getKHolder(): KInventoryHolder

    /**
     * Constructs the bukkit inventory from this KInventory with the current set content
     * @return the bukkit inventory
     */
    abstract fun toBukkitInventory(): Inventory

    /**
     * Returns all the items in the inventory which have been set
     * @warning items which have been added from players are not in this map
     * @return a map where the slot is mapped to the item
     */
    abstract fun getItems(): Map<Int, KItem>

    /**
     * @return the first KRow which contains the given KItem
     */
    abstract fun getRowForItem(item: KItem): KRow?

    /**
     * @return the slot for the given item, -1 if item is not set yet
     */
    abstract fun getSlotForItem(item: KItem): Int

    /**
     * Checks whether the inventory is private.
     * Private indicated that only one player at a time can open the same reference to a KInventory
     * @return boolean
     */
    abstract fun isPrivate(): Boolean

    /**
     * @return if the inventory is currently opened or closed
     */
    abstract fun isOpened(): Boolean

    /**
     * Sets the inventory to closed
     * Should be called when the inventory has been closed.
     * @param player who closed the inventory
     */
    abstract fun closed(player: Player)

    /**
     * Executes the passed function, when a bukkit [org.bukkit.inventory.ItemStack] is clicked
     *
     * This function will always be called, independent of the Item clicked, whether it is registered or not
     *
     * @param action function to execute when the condition is met
     */
    abstract fun onItemClicked(action: KInventory.(item: ItemStack, player: Player, event: InventoryClickEvent) -> Unit)

    /**
     * Notifies the inventory that the given item has been clicked by the given player
     * @param item which got clicked
     * @param player who clicked
     */
    abstract fun itemClicked(item: ItemStack, player: Player, event: InventoryClickEvent)

    /**
     * Checks whether the given inventory is the same inventory
     * @param inventory the bukkit inventory
     */
    abstract fun isEqual(inventory: Inventory): Boolean
}