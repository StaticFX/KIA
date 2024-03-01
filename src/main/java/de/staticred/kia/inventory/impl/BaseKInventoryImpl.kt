package de.staticred.kia.inventory.impl

import de.staticred.kia.inventory.InventoryManager
import de.staticred.kia.inventory.KInventory
import de.staticred.kia.inventory.KInventoryHolder
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryHolder

class BaseKInventoryImpl(owner: InventoryHolder?, private val private: Boolean): BaseKInventory(owner) {
    constructor(owner: KInventoryHolder, private: Boolean, size: Int, type: InventoryType? = null, title: Component? = null): this(owner, private) {
        holder = owner

        bukkitInventory = when (type) {
            null -> { Bukkit.createInventory(owner, size, title ?: Component.empty()) }
            else -> { Bukkit.createInventory(owner, type, title ?: Component.empty()) }
        }

        holder.inventory = bukkitInventory
        this.size = size
        InventoryManager.addInventory(this)
    }

    override fun isPrivate(): Boolean {
        return private
    }
}