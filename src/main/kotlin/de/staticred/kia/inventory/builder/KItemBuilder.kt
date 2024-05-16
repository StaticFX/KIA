package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.item.DraggingMode
import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.inventory.item.KItemImpl
import org.bukkit.Material

/**
 * Builds a new kItem
 * @param material of the item
 * @param amount of the item
 * @param init init function
 * @return newly built kItem
 */
fun kItem(material: Material, amount: Int = 1, init: KItem.() -> Unit): KItem {
    return KItemImpl(DraggingMode.NONE, material, amount).apply(init)
}

/**
 * Builds a new kItem
 * @param material of the item
 * @param amount of the item
 * @return newly build kItem
 */
fun kItem(material: Material, amount: Int = 1): KItem {
    return KItemImpl(DraggingMode.NONE, material, amount)
}