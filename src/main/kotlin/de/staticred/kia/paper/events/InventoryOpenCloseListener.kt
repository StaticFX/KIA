package de.staticred.kia.paper.events

import de.staticred.kia.inventory.InventoryManager
import de.staticred.kia.inventory.KInventoryHolder
import de.staticred.kia.inventory.KPageInventory
import de.staticred.kia.inventory.events.OpenEventData
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

/**
 * Util class to listened to the inventory open close events from bukkit
 */
class InventoryOpenCloseListener: Listener {

    @EventHandler
    fun onInventoryOpened(event: InventoryOpenEvent) {
        val holder = event.inventory.holder ?: return

        if (holder is KInventoryHolder) {
            val inventoryID = holder.uuid
            if (!InventoryManager.isInventory(inventoryID))
                event.isCancelled = true

            val kInventory = InventoryManager.getInventory(inventoryID)

            if (kInventory.isPrivate()) {
                if (InventoryManager.isOpened(kInventory)) {
                    event.isCancelled = true
                    error("An private inventory has been opened multiple times. This might be an error in your code. If multiple people should access the same inventory, make it public")
                }
            }

            if (kInventory is KPageInventory) {
                event.titleOverride(kInventory.formattedTitle)
            } else {
                event.titleOverride(kInventory.title)
            }

            InventoryManager.openedInventory(holder, kInventory)
            kInventory.views += event.view
            kInventory.inventories += event.inventory
            kInventory.opened(kInventory, OpenEventData(event.player as Player))
        }
    }

    @EventHandler
    fun onInventoryClosed(event: InventoryCloseEvent) {
        val holder = event.inventory.holder ?: return

        if (holder is KInventoryHolder) {
            val inventoryID = holder.uuid
            val kInventory = InventoryManager.getInventory(inventoryID)
            InventoryManager.closedInventory(holder, kInventory)
            kInventory.closed(event.player as Player)
            kInventory.views -= event.view
            kInventory.inventories -= event.inventory
        }
    }
}