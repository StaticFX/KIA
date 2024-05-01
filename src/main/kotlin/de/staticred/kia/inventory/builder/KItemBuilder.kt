package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.item.DraggingMode
import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.inventory.item.KItemImpl
import org.bukkit.Material

fun kItem(material: Material, amount: Int = 1, init: KItem.() -> Unit): KItem {
    return KItemImpl(DraggingMode.NONE, material, amount).apply(init)
}

fun kItem(material: Material, amount: Int = 1): KItem {
    return KItemImpl(DraggingMode.NONE, material, amount)
}