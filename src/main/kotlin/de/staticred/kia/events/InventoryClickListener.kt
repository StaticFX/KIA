package de.staticred.kia.events

import de.staticred.kia.inventory.InventoryManager
import de.staticred.kia.inventory.KInventory
import de.staticred.kia.inventory.KInventoryHolder
import de.staticred.kia.inventory.item.DraggingMode
import de.staticred.kia.inventory.item.ItemManager
import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.inventory.item.KItemImpl
import de.tr7zw.changeme.nbtapi.NBT
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import java.util.*

class InventoryClickListener: Listener {

    @EventHandler
    public fun onInventoryClick(event: InventoryClickEvent) {
        val item = event.currentItem ?: return
        val inventory = event.clickedInventory ?: return
        val holder = inventory.holder ?: return

        if (holder !is KInventoryHolder) {
            return
        }

        val inventoryID = holder.getUUID()

        if (!InventoryManager.isInventory(inventoryID)) {
            event.isCancelled = true
            throw IllegalStateException("Inventory clicked which is not registered.")
        }

        val kInventory = InventoryManager.getInventory(inventoryID)
        val uuid = KItemImpl.readUUIDFromNBT(item) ?: throw IllegalStateException("None KItem clicked in KInventory")

        if (!ItemManager.hasItem(uuid)) {
            throw IllegalStateException("Clicked unregistered KItem")
        }

        val kItem = ItemManager.getItem(uuid)
        val clicker = event.whoClicked as Player
        // at this point the item is valid


        if (kItem.getDraggingMode() == DraggingMode.NONE) {
            event.isCancelled = true
        }

        if (kInventory.isInAnimation()) {
            if (kInventory.itemsClickableWhileAnimating()) {
                itemClicked(kItem, clicker, kInventory, event.slot)
            }
        } else {
            itemClicked(kItem, clicker, kInventory, event.slot)
        }
    }
}

private fun itemClicked(kItem: KItem, player: Player, kInventory: KInventory, slot: Int) {
    kItem.slot = slot
    kItem.clicked(player)
    val kRow = kInventory.getRowForItem(kItem)
    kRow?.let {
        it.setIndex(slot / 9)
        kRow.clicked(player, kItem)
    }
}