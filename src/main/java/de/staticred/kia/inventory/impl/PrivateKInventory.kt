package de.staticred.kia.inventory.impl

import de.staticred.kia.inventory.InventoryManager
import de.staticred.kia.inventory.KInventoryHolder
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.InventoryHolder

class PrivateKInventory(owner: InventoryHolder?): BaseKInventory(owner) {

    constructor(owner: InventoryHolder?, size: Int, player: Player) : this(owner) {
        holder = KInventoryHolder(InventoryManager.generateRandomInventoryID(), player)
        bukkitInventory = Bukkit.createInventory(holder, size)
        holder.inventory = bukkitInventory
        this.size = size
        InventoryManager.addInventory(this)
    }

    constructor(owner: InventoryHolder?, size: Int, title: Component, player: Player) : this(owner) {
        holder = KInventoryHolder(InventoryManager.generateRandomInventoryID(), player)
        bukkitInventory = Bukkit.createInventory(holder, size, title)
        holder.inventory = bukkitInventory
        this.size = size
        InventoryManager.addInventory(this)
    }

    constructor(owner: InventoryHolder?, type: InventoryType, player: Player) : this(owner) {
        holder = KInventoryHolder(InventoryManager.generateRandomInventoryID(), player)
        bukkitInventory = Bukkit.createInventory(holder, type)
        holder.inventory = bukkitInventory
        this.size = bukkitInventory.size
        InventoryManager.addInventory(this)
    }

    constructor(owner: InventoryHolder?, type: InventoryType, title: Component, player: Player) : this(owner) {
        holder = KInventoryHolder(InventoryManager.generateRandomInventoryID(), player)
        bukkitInventory = Bukkit.createInventory(holder, type, title)
        holder.inventory = bukkitInventory
        this.size = bukkitInventory.size
        InventoryManager.addInventory(this)
    }

    override fun isPrivate(): Boolean {
        return true
    }
}