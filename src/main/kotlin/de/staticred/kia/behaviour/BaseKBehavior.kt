package de.staticred.kia.behaviour

import de.staticred.kia.util.KIdentifier

abstract class BaseKBehavior<T, C: KBehaviorContext>(
    override val identifier: KIdentifier
) : KBehavior<T, C> {

    var executor: (T.(C) -> Unit)? = null

    override fun behave(block: T.(context: C) -> Unit) {
        executor = block
    }

    override fun run(target: T, context: C) {
        val impl = executor ?: return
        target.impl(context)
    }
}