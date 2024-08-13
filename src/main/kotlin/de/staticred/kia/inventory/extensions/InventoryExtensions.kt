package de.staticred.kia.inventory.extensions

import de.staticred.kia.inventory.InventoryManager
import de.staticred.kia.inventory.kinventory.KInventory
import de.staticred.kia.inventory.KInventoryHolder
import org.bukkit.inventory.Inventory

/**
 * Converts to a registered KInventory
 *
 * @return inventory if registered, null otherwise
 */
fun Inventory.toKInventory(): KInventory? {
    val holder = holder ?: return null

    if (holder !is KInventoryHolder) return null

    val inventoryID = holder.uuid

    if (!InventoryManager.isInventory(inventoryID)) {
        throw IllegalStateException("Inventory found which is not a KInventory but has a KInventoryHolder")
    }

    return InventoryManager.getInventory(inventoryID)
}