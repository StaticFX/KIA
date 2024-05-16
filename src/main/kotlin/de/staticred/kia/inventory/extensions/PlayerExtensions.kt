package de.staticred.kia.inventory.extensions

import de.staticred.kia.inventory.KInventory
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