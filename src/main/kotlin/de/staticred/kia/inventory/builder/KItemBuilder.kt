package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.item.DraggingMode
import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.inventory.item.RegisteredKItemImpl
import de.staticred.kia.inventory.item.StackableKItemImpl
import org.bukkit.Material

/**
 * Builds a new kItem, and registers it in the [de.staticred.kia.inventory.item.ItemManager] with the correct UUID
 * @param material of the item
 * @param amount of the item
 * @param init init function
 * @return newly built kItem, actually a [de.staticred.kia.inventory.item.RegisteredKItem]
 *
 * @author Devin
 * @since 1.0.0
 */
fun kItem(material: Material, amount: Int = 1, init: KItem.() -> Unit): KItem {
    return RegisteredKItemImpl(DraggingMode.NONE, material, amount).apply(init)
}

/**
 * Builds a new kItem, and registers it in the [de.staticred.kia.inventory.item.ItemManager] with the correct UUID
 *
 * @param material of the item
 * @param amount of the item
 * @return newly build kItem, actually a [de.staticred.kia.inventory.item.RegisteredKItem]
 *
 * @author Devin
 * @since 1.0.0
 */
fun kItem(material: Material, amount: Int = 1): KItem {
    return RegisteredKItemImpl(DraggingMode.NONE, material, amount)
}

/**
 * Builds a new [de.staticred.kia.inventory.item.StackableKItem].
 *
 * @param material of the item
 * @param amount of the item
 * @param init init function
 * @return newly build kItem, actually a [de.staticred.kia.inventory.item.RegisteredKItem]
 *
 * @author Devin
 * @since 1.0.2
 */
fun stackableKItem(material: Material, amount: Int = 1, init: KItem.() -> Unit): KItem {
    return StackableKItemImpl(DraggingMode.NONE, material, amount).apply(init)
}

/**
 * Builds a new [de.staticred.kia.inventory.item.StackableKItem].
 *
 * @param material of the item
 * @param amount of the item
 * @return newly build kItem, actually a [de.staticred.kia.inventory.item.RegisteredKItem]
 *
 * @author Devin
 * @since 1.0.2
 */
fun stackableKItem(material: Material, amount: Int = 1): KItem {
    return StackableKItemImpl(DraggingMode.NONE, material, amount)
}
