package de.staticred.kia.inventory.events

/**
 * Models a KEvent, used inside K - Structures to signal an eventable model
 *
 * @property L type of listeners
 * @property T type of the model which the event is fired on
 */
abstract class KEvent<L, T> {

    /**
     * Listeners listening to this event
     * Will be called, when the event is fired
     */
    abstract val listeners: MutableList<L>

    /**
     * This method should be overridden by the implementing event
     *
     * For example the open event should change the name to
     * > onOpen
     */
    protected fun addListener(listener: L) {
        listeners.add(listener)
    }

    /**
     * Triggers the fire event process
     *
     * Should be overridden by the implementing event
     *
     * This will notify all event listeners
     * @param model the model the event was fired on
     * @param data event data if any exists
     */
    protected abstract fun fired(model: T, data: KEventData?)
}