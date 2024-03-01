package de.staticred.kia.inventory

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import java.util.UUID

class KInventoryHolder(private val uuid: UUID, private val holder: Player): InventoryHolder {

    private lateinit var inventory: Inventory

    companion object {
        fun create(holder: Player): KInventoryHolder {
            return KInventoryHolder(InventoryManager.generateRandomInventoryID(), holder)
        }
    }

    override fun getInventory(): Inventory {
        return inventory
    }

    fun setInventory(inventory: Inventory) {
        this.inventory = inventory
    }

    fun getUUID() = uuid

}