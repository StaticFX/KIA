package de.staticred.kia.behaviour

@DslMarker
annotation class BehaviorDsl

@BehaviorDsl
class BehaviorScope {
    fun <T, C : KBehaviorContext> register(behavior: KBehavior<T, C>) {
        behavior.register()
    }

    operator fun KBehavior<*, *>.unaryPlus() {
        register()
    }
}

inline fun registerBehaviors(block: BehaviorScope.() -> Unit) {
    BehaviorScope().apply(block)
}