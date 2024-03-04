package de.staticred.kia.inventory

import de.staticred.kia.inventory.item.ItemManager
import org.bukkit.entity.Player
import java.util.UUID

object InventoryManager {


    private val customInventories = mutableListOf<KInventory>()
    private val openedPrivateKInventory = mutableMapOf<KInventoryHolder, KInventory>()

    fun generateRandomInventoryID(): UUID {
        var id = UUID.randomUUID()
        while (isInventory(id)) id = UUID.randomUUID()
        return id
    }

    fun addInventory(inventory: KInventory) {
        customInventories += inventory
    }

    fun isInventory(uuid: UUID): Boolean = customInventories.any { it.getHolder().getUUID() == uuid }

    fun isInventory(kInventory: KInventory): Boolean = customInventories.any { it == kInventory }

    fun removeInventory(uuid: UUID) {
        val inv = getInventory(uuid)

        for (item in inv.getItems().values) {
            ItemManager.removeItem(item.uuid())
        }
        customInventories -= inv
    }

    fun openedInventory(player: KInventoryHolder, kInventory: KInventory) {
        if (kInventory.isPrivate())
            openedPrivateKInventory[player] = kInventory
    }

    fun closedInventory(player: KInventoryHolder, kInventory: KInventory) {
        if (kInventory.isPrivate())
            openedPrivateKInventory.remove(player)
    }

    fun isOpened(kInventory: KInventory): Boolean {
        return openedPrivateKInventory.values.any { it == kInventory }
    }

    fun getInventory(uuid: UUID) = customInventories.first { it.getHolder().getUUID() == uuid }

}