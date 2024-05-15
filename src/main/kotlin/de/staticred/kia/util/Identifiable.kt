package de.staticred.kia.util

/**
 * Models an identifiable
 * @param T the id generic
 */
interface Identifiable<T> {

    /**
     * Sets the id of the identifiable
     * @param id the id
     */
    @Deprecated("Will be replaced with a variable")
    fun setID(id: T)

    /**
     * @return the id of the model
     */
    @Deprecated("Will be replaced with a variable")
    fun getID(): T?

    /**
     * @return if the id is null or not
     */
    fun hasID(): Boolean

}