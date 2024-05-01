package de.staticred.kia.inventory.impl

import de.staticred.kia.inventory.*
import de.staticred.kia.inventory.item.KItem
import net.kyori.adventure.text.Component

class KPageImpl(override var title: Component?): KPage {
    override var header: PageController? = null
    override var footer: PageController? = null
    override var content: MutableMap<Int, KItem> = mutableMapOf()

    private val openingListeners = mutableListOf<KPage.(parent: PageKInventory) -> Unit>()
    private val closingListeners = mutableListOf<KPage.(parent: PageKInventory) -> Unit>()

    override fun setItem(slot: Int, item: KItem) {
        content[slot] = item
    }

    override fun hasHeader(): Boolean {
        return header != null
    }

    override fun hasFooter(): Boolean {
        return footer != null
    }

    override fun opened(inventory: PageKInventory) {
       openingListeners.forEach { it(inventory) }
    }

    override fun closed(inventory: PageKInventory) {
        closingListeners.forEach { it(inventory) }
    }

    override fun onOpened(action: KPage.(parent: PageKInventory) -> Unit) {
        openingListeners += action
    }

    override fun onClosed(action: KPage.(parent: PageKInventory) -> Unit) {
        closingListeners += action
    }

}