package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.KInventory
import de.staticred.kia.inventory.KInventoryHolder
import de.staticred.kia.inventory.impl.BaseKInventoryImpl
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType


fun kInventory(holder: Player, size: Int, type: InventoryType, init: KInventory.() -> Unit): KInventory {
    return BaseKInventoryImpl(KInventoryHolder.create(holder), size, type, null).apply(init)
}