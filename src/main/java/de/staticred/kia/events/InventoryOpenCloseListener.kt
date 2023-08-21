package de.staticred.kia.events

import de.staticred.kia.inventory.InventoryManager
import de.staticred.kia.inventory.KInventoryHolder
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent

class InventoryOpenCloseListener: Listener {

    @EventHandler
    public fun onInventoryOpened(event: InventoryOpenEvent) {
        val holder = event.inventory.holder ?: return

        if (holder is KInventoryHolder) {
            val inventoryID = holder.getUUID()
            if (!InventoryManager.isInventory(inventoryID))
                event.isCancelled = true


            val kInventory = InventoryManager.getInventory(inventoryID)

            if (kInventory.isPrivate()) {
                if (InventoryManager.isOpened(kInventory)) {
                    event.isCancelled = true
                    error("An private inventory has been opened multiple times. This might be an error in your code. If multiple people should access the same inventory, make it public")
                }
            }

            InventoryManager.openedInventory(holder, kInventory)
            kInventory.opened()
        }
    }

    @EventHandler
    public fun onInventoryClosed(event: InventoryCloseEvent) {
        val holder = event.inventory.holder ?: return

        if (holder is KInventoryHolder) {
            val inventoryID = holder.getUUID()
            val kInventory = InventoryManager.getInventory(inventoryID)
            InventoryManager.closedInventory(holder, kInventory)
            kInventory.closed()
        }
    }
}