package de.staticred.kia.inventory.impl

import de.staticred.kia.inventory.BaseKInventory
import de.staticred.kia.inventory.InventoryManager
import de.staticred.kia.inventory.KInventoryHolder
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryHolder

open class BaseKInventoryImpl(owner: KInventoryHolder, type: InventoryType?, title: Component?, private val private: Boolean = true): BaseKInventory(owner, title) {

    init {
        holder = owner

        bukkitInventory = when (type) {
            null -> { Bukkit.createInventory(owner, size, title ?: Component.empty()) }
            else -> { Bukkit.createInventory(owner, type, title ?: Component.empty()) }
        }

        holder.inventory = bukkitInventory

        val id = InventoryManager.generateRandomInventoryID()
        this.setID(id)
        println("Adding inventory ${this.getID()}")
        InventoryManager.addInventory(this)
    }

    override fun isPrivate(): Boolean {
        return private
    }
}