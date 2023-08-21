package de.staticred.kia.inventory.item

import java.util.UUID

object ItemManager {

    private val items = mutableListOf<KItem>()


    fun generateID(): UUID {
        var id = UUID.randomUUID()
        while (items.any { it.uuid == id }) id = UUID.randomUUID()
        return id
    }

    fun addItem(item: KItem) {
        items += item
    }

    fun removeItem(uuid: UUID) {
        items.removeIf { it.uuid == uuid }
    }

    fun hasItem(uuid: UUID): Boolean = items.any { it.uuid == uuid }

    fun getItem(uuid: UUID): KItem {
        return items.first { it.uuid == uuid }
    }
}