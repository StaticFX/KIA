package de.staticred.kia.behaviour.item

import de.staticred.kia.behaviour.BaseKBehavior
import de.staticred.kia.behaviour.KBehaviorContext
import de.staticred.kia.util.KIdentifier
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class KItemBehavior(identifier: KIdentifier): BaseKBehavior<ItemStack, KItemBehaviorContext>(identifier)

class KItemBehaviorContext(
    val player: Player,
    val action: Action,
    val rawEvent: PlayerInteractEvent,
) : KBehaviorContext