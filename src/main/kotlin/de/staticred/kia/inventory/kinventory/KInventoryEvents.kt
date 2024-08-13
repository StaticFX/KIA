package de.staticred.kia.inventory.kinventory

import de.staticred.kia.inventory.InventoryContentContainer
import de.staticred.kia.inventory.events.OpenEvent
import de.staticred.kia.inventory.events.OpenEventData
import org.bukkit.entity.Player



/**
 * Contains the events for a generic item container
 */
abstract class ContainerEvents<T>: OpenEvent<T>()