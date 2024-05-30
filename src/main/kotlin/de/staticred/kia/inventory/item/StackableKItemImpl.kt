package de.staticred.kia.inventory.item

import de.staticred.kia.inventory.KInventory
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * Example implementation of [StackableKItem]
 */
class StackableKItemImpl(draggingMode: DraggingMode, material: Material, amount: Int): KItemImpl(draggingMode, material, amount), StackableKItem {
    override fun onClick(action: KInventory.(RegisteredKItem, Player) -> Unit) {
    }
}