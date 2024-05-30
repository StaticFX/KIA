package de.staticred.kia.events

import de.staticred.kia.inventory.InventoryManager
import de.staticred.kia.inventory.KInventory
import de.staticred.kia.inventory.KInventoryHolder
import de.staticred.kia.inventory.item.*
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

/**
 * Util class to listen to the bukkit inventory click event
 */
class InventoryClickListener: Listener {

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val item = event.currentItem ?: return
        val inventory = event.clickedInventory ?: return
        val holder = inventory.holder ?: return

        if (holder !is KInventoryHolder) {
            return
        }

        val inventoryID = holder.uuid

        if (!InventoryManager.isInventory(inventoryID)) {
            event.isCancelled = true
            throw IllegalStateException("Inventory clicked which is not registered.")
        }

        val kInventory = InventoryManager.getInventory(inventoryID)
        val clicker = event.whoClicked as Player
        kInventory.itemClicked(item, clicker, event)

        val uuid = RegisteredKItemImpl.readUUIDFromNBT(item) ?: throw IllegalStateException("None KItem clicked in KInventory")
        if (!ItemManager.hasItem(uuid)) {
            return
        }

        val kItem = ItemManager.getItem(uuid)

        // at this point the item is valid
        if (kItem.draggingMode == DraggingMode.NONE) {
            event.isCancelled = true
        }

        if (kInventory.isAnimating()) {
            if (kInventory.itemClickableWhileAnimating) {
                itemClicked(kItem, clicker, kInventory, event.slot)
                event.isCancelled = true
            }
        } else {
            itemClicked(kItem, clicker, kInventory, event.slot)
        }
    }
}

private fun itemClicked(kItem: RegisteredKItem, player: Player, kInventory: KInventory, slot: Int) {
    kItem.slot = slot
    kItem.clicked(player, kInventory)
    val kRow = kInventory.getRowForItem(kItem)
    kRow?.let {
        it.index = (slot / 9)
        kRow.clicked(player, kItem)
    }
}