package de.staticred.kia.util

/**
 * Models an identifiable
 * @param T the id generic
 *
 * @since 1.0.0
 * @author Devin
 */
interface Identifiable<T> {

    /**
     * The unique ID of this identifiable
     */
    var id: T

    /**
     * @return if the id is null or not
     */
    fun hasID(): Boolean

}