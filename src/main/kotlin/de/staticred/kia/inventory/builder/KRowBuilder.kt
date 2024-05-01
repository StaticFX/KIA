package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.KRow
import de.staticred.kia.inventory.impl.KRowImpl

fun kRow(init: KRow.() -> Unit): KRow {
    return KRowImpl().apply(init)
}