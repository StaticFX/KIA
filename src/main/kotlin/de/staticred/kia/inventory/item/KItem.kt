package de.staticred.kia.inventory.item

import de.staticred.kia.inventory.AbstractContentContainer
import de.staticred.kia.inventory.KInventory
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

/**
 * Models an item in an KInventory
 *
 * KItems do only work inside KInventories.
 *
 * @see KInventory
 * @see KInventory.getItems
 *
 * Use the ItemManager to handle items
 * @see ItemManager
 *
 * Items might be uniquely identified by having UUIDs, which can be implement using NBT Tags
 * @see RegisteredKItem
 *
 * KItems don't need to have IDs, which then removes the ability to listen to their click functions
 * @see StackableKItem
 *
 * Every implementation should inherit from this from the Bukkit ItemStack class to build a correct item
 *
 * @author Devin
 * @since 1.0.0
 */
interface KItem {

    /**
     * Slot of the item in the parent inventory, if the parent inventory is set
     */
    var slot: Int

    /**
     * Determines the behaviour of the item when being dragged around the inventory
     * @see DraggingMode
     */
    var draggingMode: DraggingMode

    /**
     * The parent inventory, the item is inside of
     */
    var parent: AbstractContentContainer?

    /**
     * The custom model of this item
     */
    var model: Key?

    /**
     * @return Whether the item can be dragged or not
     */
    fun draggable(): Boolean

    /**
     * Sets the display name of the item
     * @param name the name of the item
     */
    fun setDisplayName(name: Component)

    /**
     * Sets the lore of the item
     * @param lore the lore
     */
    fun setItemLore(lore: List<Component>)

    /**
     * Enchants this item with the given parameters
     */
    fun enchant(enchantment: Enchantment, level: Int)

    /**
     * Transforms the KItem to a Bukkit ItemStack
     * @return the equivalent itemstack
     */
    fun toItemStack(): ItemStack
}