package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.KPage
import de.staticred.kia.inventory.impl.KPageImpl
import net.kyori.adventure.text.Component

fun kPage(title: Component? = null, init: KPage.() -> Unit): KPage {
    return KPageImpl(title).apply(init)
}