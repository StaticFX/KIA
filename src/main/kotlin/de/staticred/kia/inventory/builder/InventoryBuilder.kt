package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.KInventory
import de.staticred.kia.inventory.KInventoryHolder
import de.staticred.kia.inventory.impl.BaseKInventoryImpl
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType

open class InventoryBuilder(private val holder: Player) {

    private var size = 0
    private var type: InventoryType? = null
    private var public = false
    private var title: Component? = null

    fun setSize(size: Int): InventoryBuilder {
        this.size = size
        return this
    }

    fun setType(inventoryType: InventoryType): InventoryBuilder {
        this.type = inventoryType
        return this
    }

    fun setPublic(): InventoryBuilder {
        public = true
        return this
    }

    fun setPrivate(): InventoryBuilder {
        public = false
        return this
    }

    fun setTitle(title: Component): InventoryBuilder {
        this.title = title
        return this
    }


    open fun build(): KInventory {
        val kInventoryHolder = KInventoryHolder.create(holder)
        return BaseKInventoryImpl(kInventoryHolder, !public, size, type, title)
    }
}