package de.staticred.kia.events

import de.staticred.kia.inventory.extensions.toKInventory
import de.staticred.kia.inventory.item.DraggingMode
import de.staticred.kia.inventory.item.ItemManager
import de.staticred.kia.inventory.item.KItemImpl
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryMoveItemEvent

/**
 * Util class to listen to the bukkit inventory move event
 */
class InventoryMoveItemListener: Listener {

    @EventHandler
    public fun onInventoryMoveItem(event: InventoryMoveItemEvent) {
        val toInventory = event.destination
        val item = event.item

        val kInventory = event.initiator.toKInventory() ?: return

        val uuid = KItemImpl.readUUIDFromNBT(item) ?: throw IllegalStateException("None KItem clicked in KInventory")

        if (!ItemManager.hasItem(uuid)) {
            throw IllegalStateException("Clicked unregistered KItem")
        }

        val kItem = ItemManager.getItem(uuid)

        when (kItem.getDraggingMode()) {
            DraggingMode.IN_INVENTORY -> { if (!kInventory.isEqual(toInventory)) event.isCancelled = true }
            else -> {}
        }
    }
}