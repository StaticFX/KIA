package de.staticred.kia.util

import de.staticred.kia.inventory.builder.kItem
import org.bukkit.Material


/**
 * Item constant which is used as a blank item, instead of passing null, this item should be used
 */
val AIR_ITEM = kItem(Material.AIR)

/**
 * Will multiply the given int with 9
 */
val Int.rows: Int
    get() = this  * 9