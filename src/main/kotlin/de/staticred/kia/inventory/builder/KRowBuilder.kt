package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.KRow
import de.staticred.kia.inventory.impl.KRowImpl

fun kRow(name: String = "", init: KRow.() -> Unit): KRow {
    return KRowImpl(name).apply(init)
}