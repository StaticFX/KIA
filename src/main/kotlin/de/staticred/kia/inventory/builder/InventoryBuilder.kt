package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.kinventory.KInventory
import de.staticred.kia.inventory.KInventoryHolder
import de.staticred.kia.inventory.impl.BaseKInventoryImpl
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType

/**
 * Builds a new simple KInventory
 * @param holder holder of the inventory
 * @param size of the inventory
 * @param type the inventorytype
 * @see InventoryType
 * @param init the init function
 * @return newly build kInventory
 *
 * @author Devin
 * @since 1.0.0
 */
fun kInventory(holder: Player, size: Int, type: InventoryType, init: KInventory.() -> Unit): KInventory {
    return BaseKInventoryImpl(KInventoryHolder.create(holder), size, null).apply(init)
}