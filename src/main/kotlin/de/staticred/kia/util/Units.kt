package de.staticred.kia.util

import de.staticred.kia.inventory.builder.kItem
import org.bukkit.Material


val AIR_ITEM = kItem(Material.AIR)

/**
 * Will multiply the given int with 9
 */
val Int.rows: Int
    get() = this  * 9