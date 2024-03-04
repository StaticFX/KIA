package de.staticred.kia.inventory.extensions

import de.staticred.kia.inventory.InventoryManager
import de.staticred.kia.inventory.KInventory
import de.staticred.kia.inventory.KInventoryHolder
import org.bukkit.inventory.Inventory

fun Inventory.toKInventory(): KInventory? {
    val holder = holder ?: return null

    if (holder !is KInventoryHolder) return null

    val inventoryID = holder.getUUID()

    if (!InventoryManager.isInventory(inventoryID)) {
        throw IllegalStateException("Inventory found which is not a KInventory but has a KInventoryHolder")
    }

    return InventoryManager.getInventory(inventoryID)
}