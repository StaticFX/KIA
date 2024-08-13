package de.staticred.kia.inventory

import de.staticred.kia.inventory.item.ItemManager
import de.staticred.kia.inventory.item.RegisteredKItem
import de.staticred.kia.inventory.kinventory.KInventory
import java.util.UUID

/**
 * Util class to handle inventories using their holders and uuids
 *
 * These functions are handled by KIA, and the inventory events.
 *
 * @author Devin
 * @since 1.0.0
 */
object InventoryManager {

    private val customInventories = mutableListOf<KInventory>()
    private val openedPrivateKInventory = mutableMapOf<KInventoryHolder, KInventory>()

    /**
     * Generates a random unused uuid for an inventory
     * @return the uuid
     */
    fun generateRandomInventoryID(): UUID {
        var id = UUID.randomUUID()
        while (isInventory(id)) id = UUID.randomUUID()
        return id
    }

    /**
     * Registers a new custom inventory
     * @param inventory the inventory to register
     */
    fun addInventory(inventory: KInventory) {
        customInventories += inventory
    }

    /**
     * Checks if any inventory can be associated with the given id
     * @return true if there is any inventory with the uuid, false otherwise
     */
    fun isInventory(uuid: UUID): Boolean = customInventories.any { it.getKHolder().uuid == uuid }

    /**
     * Checks if the given inventory instance is a registered inventory
     * @return true if the inventory is registered, false otherwise
     */
    fun isInventory(kInventory: KInventory): Boolean = customInventories.any { it == kInventory }

    /**
     * Removes the inventory associated with the given id and each item
     * @see ItemManager
     * @param uuid of the inventory
     */
    fun removeInventory(uuid: UUID) {
        val inv = getInventory(uuid)

        for (item in inv.getItems().values) {
            if (item is RegisteredKItem)
                item.id.let { ItemManager.removeItem(it) }
        }
        customInventories -= inv
    }

    /**
     * Saves that the current player has opened the given inventory
     * @param player the player
     * @param kInventory the inventory which he opened
     */
    fun openedInventory(player: KInventoryHolder, kInventory: KInventory) {
        openedPrivateKInventory[player] = kInventory
    }

    /**
     * Removes the information that a player has opened an inventory
     * @param player who closed the inventory
     * @param kInventory the inventory he closed
     */
    fun closedInventory(player: KInventoryHolder, kInventory: KInventory) {
        if (kInventory.isPrivate())
            openedPrivateKInventory.remove(player)
    }

    /**
     * Checks if the given inventory is opened
     * @param kInventory the inventory to check
     * @return true if the inventory is opened, false otherwise
     */
    fun isOpened(kInventory: KInventory): Boolean {
        return openedPrivateKInventory.values.any { it == kInventory }
    }

    /**
     * Gets the first inventory with the given id
     * @param uuid the inventory id
     * @return the associated inventory
     */
    fun getInventory(uuid: UUID) = customInventories.first { it.getKHolder().uuid == uuid }
}