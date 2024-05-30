package de.staticred.kia.inventory.impl

import de.staticred.kia.inventory.BaseKInventory
import de.staticred.kia.inventory.InventoryManager
import de.staticred.kia.inventory.KInventoryHolder
import net.kyori.adventure.text.Component
import org.bukkit.event.inventory.InventoryType

open class BaseKInventoryImpl(owner: KInventoryHolder, size: Int = 3*9, type: InventoryType?, title: Component?, private val private: Boolean = true): BaseKInventory(owner, title, size) {

    init {
        holder.inventory = bukkitInventory
        InventoryManager.addInventory(this)
    }

    override fun isPrivate(): Boolean {
        return private
    }
}