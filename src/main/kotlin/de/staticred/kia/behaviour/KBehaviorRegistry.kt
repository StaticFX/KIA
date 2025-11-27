package de.staticred.kia.behaviour

import de.staticred.kia.KIA

/**
 * A registry for managing and storing `KBehavior` instances, allowing them to be
 * registered and retrieved by their unique identifiers.
 *
 * This singleton provides mechanisms for adding new behaviors into the registry
 * and fetching them dynamically at runtime using a unique string identifier.
 */
object KBehaviorRegistry {
    private val behaviors = mutableMapOf<String, KBehavior<*, *>>()

    /**
     * Registers a new behavior into the behavior registry. If a behavior with the same
     * identifier already exists, it will be overwritten with the new behavior.
     *
     * @param T The type of the target this behavior is applied to.
     * @param C The type of the context in which this behavior operates. Must implement KBehaviorContext.
     * @param behavior The behavior to be registered. Contains the identifier and the logic
     *                 to be associated with the specified target and context.
     */
    fun <T, C: KBehaviorContext> register(behavior: KBehavior<T, C>) {
        if (behaviors.containsKey(behavior.identifier.build())) {
            KIA.plugin.logger.warning("Behavior with identifier ${behavior.identifier.build()} already registered! Overwriting it")
        }

        behaviors[behavior.identifier.build()] = behavior
    }

    @Suppress("UNCHECKED_CAST")
    fun <T, C: KBehaviorContext> get(id: String): KBehavior<T, C>? =
        behaviors[id] as? KBehavior<T, C>
}