package de.staticred.kia.inventory.item

import de.staticred.kia.inventory.kinventory.KInventory
import de.staticred.kia.util.Identifiable
import org.bukkit.entity.Player
import java.util.*

/**
 * Models a registered KItems
 *
 * Registered KItems will, when initialized, add a UUID to their NBT tags, so they can be identified later again.
 *
 * @See ItemManager
 * @since 1.0.2
 */
interface RegisteredKItem: KItem, Identifiable<UUID> {

    /**
     * Whether the item can be clicked while inside an animation or not
     */
    var clickableInAnimation: Boolean

    /**
     * Executed when the item is valid clicked in an inventory
     *
     * A valid click is when the item actually belongs to the inventory assigned to it and the item is inside a KInventory
     *
     * @param action run when the item is clicked
     */
    fun onClick(action: KInventory.(RegisteredKItem, Player) -> Unit)

    /**
     * The item has been clicked by a player
     *
     * @param player who clicked the item
     * @param kInventory the inventory the item is inside
     */
    fun clicked(player: Player, kInventory: KInventory)
}