package de.staticred.kia.inventory.item

import de.staticred.kia.inventory.KInventory
import net.kyori.adventure.text.Component
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
 * @since 1.0
 */
interface KItem {

    var slot: Int
    var clickableInAnimation: Boolean

    /**
     * Executed when the item is valid clicked in an inventory
     *
     * A valid click is when the item actually belongs to the inventory assigned to it and the item is inside a KInventory
     *
     * @param action run when the item is clicked
     */
    fun onClick(action: KInventory.(KItem, Player) -> Unit)

    /**
     * Sets the parent inventory of the item
     *
     * This happens automatically when the item is set through a KInventory
     * @param kInventory the parent
     */
    fun setParent(kInventory: KInventory)

    /**
     * @return Whether the item can be dragged or not
     */
    fun draggable(): Boolean

    /**
     * Sets the dragging mode of the item
     * @see DraggingMode
     * @param draggingMode the mode
     */
    fun setDraggingMode(draggingMode: DraggingMode)

    /**
     * gets the current dragging mode of the item
     */
    fun getDraggingMode(): DraggingMode

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
     * The item has been clicked by a player
     *
     * @param player who clicked the item
     */
    fun clicked(player: Player)

    /**
     * UUID of the item
     * @return the uuid
     */
    fun uuid(): UUID

    /**
     * Transforms the KItem to a Bukkit ItemStack
     * @return the equivalent itemstack
     */
    fun toItemStack(): ItemStack
}