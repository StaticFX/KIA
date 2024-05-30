package de.staticred.kia.inventory.item

import de.staticred.kia.inventory.AbstractContentContainer
import de.staticred.kia.inventory.InventoryContentContainer
import de.staticred.kia.inventory.KInventory
import net.kyori.adventure.text.Component
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

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
 * Items are uniquely identified by having UUIDs, which can be implement using NBT Tags
 * @see KItemImpl
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
     * Whether the item can be clicked while inside an animation or not
     */
    var clickableInAnimation: Boolean


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
     * Executed when the item is valid clicked in an inventory
     *
     * A valid click is when the item actually belongs to the inventory assigned to it and the item is inside a KInventory
     *
     * @param action run when the item is clicked
     */
    fun onClick(action: KInventory.(RegisteredKItem, Player) -> Unit)

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
     * The item has been clicked by a player
     *
     * @param player who clicked the item
     * @param kInventory the inventory the item is inside
     */
    fun clicked(player: Player, kInventory: KInventory)

    /**
     * Transforms the KItem to a Bukkit ItemStack
     * @return the equivalent itemstack
     */
    fun toItemStack(): ItemStack
}