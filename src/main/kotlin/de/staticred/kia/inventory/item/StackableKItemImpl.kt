package de.staticred.kia.inventory.item

import org.bukkit.Material

/**
 * Example implementation of [StackableKItem]
 */
class StackableKItemImpl(draggingMode: DraggingMode, material: Material, amount: Int): KItemImpl(draggingMode, material, amount), StackableKItem