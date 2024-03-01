package de.staticred.kia.events

import de.staticred.kia.inventory.InventoryManager
import de.staticred.kia.inventory.KInventoryHolder
import de.staticred.kia.inventory.item.ItemManager
import de.staticred.kia.inventory.item.KItem
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

        if (holder is KInventoryHolder) {
            val inventoryID = holder.getUUID()
            if (!InventoryManager.isInventory(inventoryID))
                event.isCancelled = true

            val kInventory = InventoryManager.getInventory(inventoryID)


            val uuid: UUID = NBT.get(item) {

                if (it.hasTag("UUID"))
                    return@get UUID.fromString(it.getString("UUID"))
                return@get null
            } ?: throw IllegalStateException("None KItem clicked in KInventory")

            if (!ItemManager.hasItem(uuid)) {
                throw IllegalStateException("Clicked unregistered KItem")
            }

            val kItem = ItemManager.getItem(uuid)

            if (!kItem.draggable) event.isCancelled = true

            if (!kInventory.isInAnimation()) {
                kItem.clicked(event.whoClicked as Player)
                val kRow = kInventory.getRowForItem(kItem)

                kRow?.let { kRow.clicked(event.whoClicked as Player) }
            }
        }
    }
}