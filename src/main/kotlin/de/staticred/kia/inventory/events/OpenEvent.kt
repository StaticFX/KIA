package de.staticred.kia.inventory.events
import org.bukkit.entity.Player

/**
 * Models an abstract open event
 *
 * Used when a container is opened
 * @property T the container model
 */
open class OpenEvent<T>: KEvent<T.(event: OpenEventData) -> Unit, T>() {

    private val openingListeners: MutableList<T.(event: OpenEventData) -> Unit> = mutableListOf()
    override val listeners: MutableList<T.(event: OpenEventData) -> Unit> = openingListeners

    /**
     * Change fired event data to use [OpenEventData]
     * @see KEvent.fired
     */
    fun firedOpenEvent(model: T, data: OpenEventData) = fired(model, data)

    /**
     * Listens to the opening event
     */
    open fun onOpened(listener: T.(event: OpenEventData) -> Unit) = addListener(listener)

    override fun fired(model: T, data: KEventData?) {
        listeners.forEach { it(model, data as OpenEventData) }
    }

    fun opened(model: T, data: OpenEventData) {
        fired(model, data)
    }
}

/**
 * Event data for the [OpenEvent]
 * @property player the player who opened the container
 */
data class OpenEventData(val player: Player): KEventData