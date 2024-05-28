package de.staticred.kia.events

import de.staticred.kia.inventory.AbstractContentContainer
import de.staticred.kia.inventory.extensions.toKInventory
import de.staticred.kia.inventory.item.DraggingMode
import de.staticred.kia.inventory.item.ItemManager
import de.staticred.kia.inventory.item.KItemImpl
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryDragEvent


/**
 * Util class to listen to the item drag event
 */
class InventoryDragItemListener: Listener {

    @EventHandler
    public fun onInventoryDrag(event: InventoryDragEvent) {
        val player = event.whoClicked as Player
        val inventory = event.inventory

        val kInventory = inventory.toKInventory() ?: return

        for ((slot, item) in event.newItems) {
            val uuid = KItemImpl.readUUIDFromNBT(item) ?: error("None KItem dragged in KInventory")
            if (!ItemManager.hasItem(uuid)) error("KItem clicked which is not registered in ItemManager")

            val kItem = ItemManager.getItem(uuid)
            val initSlot = kInventory.getSlotForItem(kItem)


            val contentContainer = kInventory as AbstractContentContainer
            val rowLength = contentContainer.rowLength

            println(slot)
            println(kInventory.size - 1)

            when (kItem.draggingMode) {
                DraggingMode.NONE -> event.isCancelled = true
                else -> {}
            }
        }
    }
}