package de.staticred.kia.util

interface Identifiable<T> {

    fun setID(id: T)

    fun getID(): T?

    fun hasID(): Boolean

}