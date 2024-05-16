package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.KInventoryHolder
import de.staticred.kia.inventory.KPage
import de.staticred.kia.inventory.KPageInventory
import de.staticred.kia.inventory.impl.KPageInventoryImpl
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

/**
 * Builds a new kPageInventory
 *
 * @param holder the inventory holder
 * @param size the size of the inventory
 * @param init init function
 * @return new KPageInventory
 */
fun kPageInventory(holder: Player, size: Int = 3*9, init: KPageInventory.() -> Unit): KPageInventory {
    return KPageInventoryImpl(KInventoryHolder.create(holder), size,true, Component.empty()).apply(init)
}
