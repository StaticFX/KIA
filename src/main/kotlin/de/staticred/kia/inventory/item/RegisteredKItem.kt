package de.staticred.kia.inventory.item

import de.staticred.kia.behaviour.KBehavior
import de.staticred.kia.behaviour.item.KItemBehavior
import de.staticred.kia.behaviour.item.KItemBehaviorContext
import de.staticred.kia.inventory.KInventory
import de.staticred.kia.util.Identifiable
import de.staticred.kia.util.KIdentifier
import de.staticred.kia.util.RuntimeFunction
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
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
     * The item has been clicked by a player inside an inventory
     *
     * @param player who clicked the item
     * @param kInventory the inventory the item is inside
     */
    fun clicked(
        player: Player,
        kInventory: KInventory,
    )

    /**
     * Executed when the player left-clicked with this item outside an inventory
     *
     *
     * The code block will receive a [PlayerInteractEvent] which is validated
     * to have a valid item and a valid click.
     *
     * Keep in mind, this function only works in the same runtime as the item was generated.
     * If the server is restarted, this will not trigger on the item.
     * To still trigger the action, please use the [addBehavior] function.
     *
     * @param action code block which is executed
     */
    @RuntimeFunction
    fun onLeftClick(action: RegisteredKItem.(Player, PlayerInteractEvent) -> Unit)


    /**
     * Executed when the player right-clicked with this item outside an inventory
     *
     *
     * The code block will receive a [PlayerInteractEvent] which is validated
     * to have a valid item and a valid click.
     *
     * Keep in mind, this function only works in the same runtime as the item was generated.
     * If the server is restarted, this will not trigger on the item.
     * To still trigger the action, please use the [addBehavior] function.
     *
     * @param action code block which is executed
     */
    @RuntimeFunction
    fun onRightClick(action: RegisteredKItem.(Player, PlayerInteractEvent) -> Unit)

    /**
     * Adds a behavior to the item with a specific identifier and functionality block.
     *
     * This method registers a new behavior to the item, allowing custom functionality
     * when specific actions or events are triggered on the item. The added behavior
     * persists through the item's lifecycle and is identified using the provided identifier.
     *
     * @see KBehavior
     *
     * @param identifier the unique identifier for the behavior
     * @param block the functionality block defining the behavior, receiving an ItemStack
     *              and a context of type KItemBehaviorContext
     * @return the created KItemBehavior instance associated with the registered behavior
     */
    fun addBehavior(identifier: KIdentifier, block: ItemStack.(context: KItemBehaviorContext) -> Unit): KItemBehavior


    /**
     * Adds a behavior to the item.
     *
     * This method registers the provided behavior to the item, allowing it
     * to respond to certain actions or events. The added behavior is tied
     * to the item's functionality and may persist through the item's lifecycle.
     *
     * @param kItemBehavior the behavior to add
     * @return the added behavior instance
     */
    fun addBehavior(kItemBehavior: KItemBehavior): KItemBehavior

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
