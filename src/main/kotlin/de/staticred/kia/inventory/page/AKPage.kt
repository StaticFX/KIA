package de.staticred.kia.inventory.page

import de.staticred.kia.animation.Animatable
import de.staticred.kia.inventory.AbstractContentContainer
import de.staticred.kia.inventory.InventoryContentContainer
import de.staticred.kia.inventory.KPage
import de.staticred.kia.inventory.KRow

/**
 * Abstract wrapper class for KPages
 */
abstract class AKPage(rowLength: Int, size: Int): KPage, Animatable<KPage>, AbstractContentContainer<KPage>(rowLength, size) {

    /**
     * Returns the given row for the index as a new object
     * Parent of the row will only be set, if the page has a parent as well
     * @param index index of the row
     * @return new KRow instance
     */
    abstract override fun getRowFor(index: Int): KRow
}