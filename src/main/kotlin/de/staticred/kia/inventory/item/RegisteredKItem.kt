package de.staticred.kia.inventory.item

import de.staticred.kia.inventory.KInventory
import de.staticred.kia.util.Identifiable
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import java.util.*

/**
 * Models a registered KItems
 *
 * Registered KItems will, when initialized
 * add a UUID to their NBT tags, so they can be identified later again.
 *
 * This allows the item to be tracked inside bukkit events, which allows
 * for event listeners on this item
 *
 * @See ItemManager
 * @since 1.0.2
 */
interface RegisteredKItem :
    KItem,
    Identifiable<UUID> {
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
    fun clicked(
        player: Player,
        kInventory: KInventory,
    )

    /**
     * Executed when the player left-clicked with this item
     *
     * This can occur outside an inventory, therefore the parent might be null
     * @see parent
     *
     * The code block will receive a [PlayerInteractEvent] which is validated
     * to have a valid item and a valid click.
     *
     * @param action code block which is executed
     */
    fun onLeftClick(action: RegisteredKItem.(Player, PlayerInteractEvent) -> Unit)

    /**
     * Executed when the player right-clicked with this item
     *
     * This can occur outside an inventory, therefore the parent might be null
     * @see parent
     *
     * The code block will receive a [PlayerInteractEvent] which is validated
     * to have a valid item and a valid click.
     *
     * @param action code block which is executed
     */
    fun onRightClick(action: RegisteredKItem.(Player, PlayerInteractEvent) -> Unit)

    /**
     * When the item has been left-clicked by a player
     * @param player who clicked
     * @param event the bukkit event
     */
    fun leftClicked(
        player: Player,
        event: PlayerInteractEvent,
    )

    /**
     * When the item has been right-clicked by a player
     * @param player who clicked
     * @param event the bukkit event
     */
    fun rightClicked(
        player: Player,
        event: PlayerInteractEvent,
    )
}
