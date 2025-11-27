package de.staticred.kia.events

import de.staticred.kia.behaviour.KBehaviorContext
import de.staticred.kia.behaviour.KBehaviorRegistry
import de.staticred.kia.behaviour.item.KItemBehaviorContext
import de.staticred.kia.inventory.item.RegisteredKItemImpl
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack

class PlayerInteractListener : Listener {
    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val item = event.item ?: return
        val player = event.player

        val nbtData = RegisteredKItemImpl.readNBTDataFromItem(item) ?: return

        val behaviours = nbtData.behaviours.mapNotNull { KBehaviorRegistry.get<ItemStack, KBehaviorContext>(it) }

        if (behaviours.isEmpty()) return

        val context = KItemBehaviorContext(player, event.action, event)

        behaviours.forEach { it.run(item, context) }
    }
}
