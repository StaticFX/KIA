package de.staticred.kia.inventory

import de.staticred.kia.inventory.impl.PrivateKInventory
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType

class InventoryBuilder(val holder: Player) {

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


    fun build(): KInventory {
        if (!public) {
            if (type == null) {
                if (size == 0)
                    return PrivateKInventory(holder)

                if (title == null)
                    return PrivateKInventory(holder, size, holder)

                return PrivateKInventory(holder, size, title!!, holder)
            }

            if (title == null)
                return PrivateKInventory(holder, type!!, holder)

            return PrivateKInventory(holder, type!!, title!!, holder)

        } else {
            TODO()
        }
    }



}