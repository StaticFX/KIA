package de.staticred.kia.inventory.item

import de.staticred.kia.util.Identifiable
import java.util.*

/**
 * Models a registered KItems
 *
 * Registered KItems will, when initialized, add a UUID to their NBT tags, so they can be identified later again.
 *
 * @See ItemManager
 * @since 1.0.2
 */
interface RegisteredKItem: KItem, Identifiable<UUID> {
}