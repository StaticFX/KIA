package de.staticred.kia.inventory.item

import java.util.UUID

/**
 * Util class to handle items across inventories
 *
 * @author Devin
 * @since 1.0.0
 */
object ItemManager {

    private val items = mutableListOf<RegisteredKItem>()

    /**
     * Generates a new unused id which can be used for an item
     * @return the id
     */
    fun generateID(): UUID {
        var id = UUID.randomUUID()
        while (items.any { it.id == id }) id = UUID.randomUUID()
        return id
    }

    /**
     * Registers a new item
     * @param item the item to register
     */
    fun addItem(item: RegisteredKItem) {
        items += item
    }

    /**
     * Removes the item from the registered items
     * @param uuid the item to remove
     */
    fun removeItem(uuid: UUID) {
        items.removeIf { it.id == uuid }
    }

    /**
     * @param uuid of item
     * @return whether the item is registered or not
     */
    fun hasItem(uuid: UUID): Boolean = items.any { it.id == uuid }

    /**
     * @param uuid of the item
     * @return the item associated to the uuid
     */
    fun getItem(uuid: UUID): RegisteredKItem {
        return items.first { it.id == uuid }
    }
}