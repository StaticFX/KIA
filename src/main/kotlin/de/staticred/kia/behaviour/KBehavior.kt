package de.staticred.kia.behaviour

import de.staticred.kia.util.KIdentifier

/**
 * Represents a behavior that can be applied to a specific type with a given context.
 *
 * @param T The type of the target this behavior is applied to.
 * @param C The type of the context in which this behavior operates. Must implement KBehaviorContext.
 *
 * KBehavior must be registered within the global behavior registry [KBehaviorRegistry].
 * To build a KBehavior you can use the [kItemBehaviour] builder function.
 *
 * @author Devin
 * @since 1.1.7
 */
interface KBehavior<T, C: KBehaviorContext> {

    /**
     * Unique identifier for a behavior. This identifier is used to uniquely
     * register a behavior within a registry and is composed of the plugin's
     * name and a specific string identifier.
     */
    val identifier: KIdentifier

    /**
     * Sets the behavior logic for a target of type [T] within a specific context of type [C].
     * The behavior logic is provided in the form of a lambda function.
     *
     * @param block A lambda defining the behavior to be applied, where the target [T] is the receiver
     *              and the [context] of type [C] is passed as a parameter.
     */
    fun behave(block: T.(context: C) -> Unit)

    /**
     * Executes the behavior logic for the specified target within the provided context.
     *
     * @param target The target of type [T] on which the behavior is applied.
     * @param context The context of type [C] in which the behavior is executed.
     */
    fun run(target: T, context: C)
}

/**
 * Registers this behavior within the global behavior registry.
 */
fun KBehavior<*, *>.register() = KBehaviorRegistry.register(this)