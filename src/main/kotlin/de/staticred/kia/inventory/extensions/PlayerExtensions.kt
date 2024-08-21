package de.staticred.kia.inventory.extensions

import de.staticred.kia.inventory.KInventory
import de.staticred.kia.inventory.item.KItem
import org.bukkit.entity.Player

/**
 * Opens the given KInventory
 *
 * @throws IllegalStateException when the given inventory is private and opened by another player
 * @param inventory the kInventory
 */
fun Player.openInventory(inventory: KInventory) {
    if (inventory.isPrivate() && inventory.isOpened()) {
        throw IllegalStateException("The supplied inventory is already opened by another player. Create a new instance to open it again.")
    }

    openInventory(inventory.toBukkitInventory())
}

/**
 * Sets the given item at the given slot in the players hotbar
 * @param slot number between 0-8 referring to the players hotbar
 * @param item the item to set
 * @throws IllegalArgumentException when slot is out of bounds
 */
fun Player.setHotbarItem(
    slot: Int,
    item: KItem,
) {
    if (slot !in 0..8) {
        throw IllegalArgumentException("Slot must be between 0 and 8")
    }

    inventory.setItem(slot, item.toItemStack())
}
