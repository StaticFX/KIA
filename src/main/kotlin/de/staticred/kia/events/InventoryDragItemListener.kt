package de.staticred.kia.events

import de.staticred.kia.inventory.extensions.toKInventory
import de.staticred.kia.inventory.item.DraggingMode
import de.staticred.kia.inventory.item.ItemManager
import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.inventory.item.KItemImpl
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryDragEvent


class InventoryDragItemListener: Listener {

    @EventHandler
    public fun onInventoryDrag(event: InventoryDragEvent) {
        val inventory = event.inventory

        val kInventory = inventory.toKInventory() ?: return

        for ((slot, item) in event.newItems) {

            val uuid = KItemImpl.readUUIDFromNBT(item) ?: error("None KItem dragged in KInventory")
            if (!ItemManager.hasItem(uuid)) error("KItem clicked which is not registered in ItemManager")

            val kItem = ItemManager.getItem(uuid)
            val initSlot = kInventory.getSlotForItem(kItem)

            println(initSlot)
            println(event.newItems)

            when (kItem.getDraggingMode()) {
                DraggingMode.IN_INVENTORY
                        -> run {
                            if (slot > inventory.size - 1) event.isCancelled = true
                        }
                DraggingMode.IN_ROW
                        -> run {
                            TODO()
                        }
                else -> {}
            }
        }
    }
}