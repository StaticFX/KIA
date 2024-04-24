package de.staticred.kia.inventory.impl

import de.staticred.kia.inventory.KPage
import de.staticred.kia.inventory.PageFooter
import de.staticred.kia.inventory.PageHeader
import de.staticred.kia.inventory.item.KItem
import net.kyori.adventure.text.Component

class KPageImpl(override var title: Component): KPage {
    override var header: PageHeader? = null
    override var footer: PageHeader? = null
    override var content: MutableMap<Int, KItem> = mutableMapOf()
    override fun setItem(slot: Int, item: KItem) {
        content[slot] = item
    }

    override fun hasHeader(): Boolean {
        return header != null
    }

    override fun hasFooter(): Boolean {
        return footer != null
    }
}