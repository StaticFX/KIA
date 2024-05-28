package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.KRow
import de.staticred.kia.inventory.impl.KRowImpl

/**
 * Builds a new KRow
 * @param name of the kRow
 * @param init init function
 * @return newly built kRow
 *
 * @author Devin
 * @since 1.0.0
 */
fun kRow(name: String = "", init: KRow.() -> Unit): KRow {
    return KRowImpl(name).apply(init)
}