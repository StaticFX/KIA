package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.KInventoryHolder
import de.staticred.kia.inventory.KPage
import de.staticred.kia.inventory.KPageInventory
import de.staticred.kia.inventory.impl.KPageInventoryImpl
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player


fun kPageInventory(holder: Player, init: KPageInventory.() -> Unit): KPageInventory {
    return KPageInventoryImpl(KInventoryHolder.create(holder), true, Component.empty()).apply(init)
}
