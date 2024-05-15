package de.staticred.kia.inventory

import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import java.util.UUID

/**
 * Extends the default bukkit inventory holder, to support a mapped UUID and
 * an associated Inventory. The mapping between the KInventory and Inventory is done using
 * the InventoryManager
 *
 * @property holder player which holds the inventory
 *
 * @see InventoryManager
 * @see KInventory
 */
class KInventoryHolder(private val uuid: UUID, val holder: Player): InventoryHolder {

    private lateinit var inventory: Inventory

    companion object {
        /**
         * Creates a new KInventoryHolder with a random id
         */
        fun create(holder: Player): KInventoryHolder {
            return KInventoryHolder(InventoryManager.generateRandomInventoryID(), holder)
        }
    }

    /**
     * @return the inventory associated with this player
     */
    override fun getInventory(): Inventory {
        return inventory
    }

    /**
     * @param inventory the inventory
     */
    fun setInventory(inventory: Inventory) {
        this.inventory = inventory
    }

    /**
     * @return uuid of this holder
     */
    fun getUUID() = uuid

}