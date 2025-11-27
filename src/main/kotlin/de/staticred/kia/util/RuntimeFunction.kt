package de.staticred.kia.util


@RequiresOptIn("This function only works in the same server runtime, as the item was generated. When the server is restarted, this will not trigger on the item. Use the addBehavior function", RequiresOptIn.Level.WARNING)
@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY
)
annotation class RuntimeFunction
