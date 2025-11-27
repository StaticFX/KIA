package de.staticred.kia.behaviour.builder

import de.staticred.kia.behaviour.item.KItemBehavior
import de.staticred.kia.behaviour.item.KItemBehaviorContext
import de.staticred.kia.util.KIdentifier
import org.bukkit.inventory.ItemStack

/**
 * Creates a new `KItemBehavior` instance and defines its behavior logic.
 *
 * @param identifier The unique identifier for the behavior, detailing the plugin and identifier string.
 * @param block A lambda function defining the behavior logic for `ItemStack` with a given context of type `KItemBehaviorContext`.
 * @return A `KItemBehavior` instance with the specified identifier and configured behavior logic.
 */
fun kItemBehavior(identifier: KIdentifier, block: ItemStack.(context: KItemBehaviorContext) -> Unit): KItemBehavior {
    return KItemBehavior(identifier).apply {
        behave(block)
    }
}