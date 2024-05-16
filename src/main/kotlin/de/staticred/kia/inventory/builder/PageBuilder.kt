package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.KPage
import de.staticred.kia.inventory.impl.KPageImpl
import net.kyori.adventure.text.Component

/**
 * Builds a new page for a paging kInventory
 *
 * @see de.staticred.kia.inventory.KPageInventory
 *
 * @param title of the page
 * @param init init function
 * @return newly built kPage
 */
fun kPage(title: Component? = null, init: KPage.() -> Unit): KPage {
    return KPageImpl(title).apply(init)
}