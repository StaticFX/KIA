package de.staticred.kia.inventory.item

/**
 * Models a stackable kItem
 *
 * The difference between [KItem] is that this item won't
 * be registered in the [ItemManager] and therefore is also not able to handle the [KItem.onClick] event
 *
 * In regard to that, the item can be stacked, also with vanilla style generated items, even if the item was
 * not generated using KIA.
 *
 * Example usage, would be an item, which can be withdrawn from the inventory.
 *
 * @since 1.0.2
 * @author Devin
 */
interface StackableKItem: KItem {
}