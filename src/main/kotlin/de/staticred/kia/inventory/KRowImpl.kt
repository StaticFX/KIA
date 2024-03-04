package de.staticred.kia.inventory

import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.inventory.item.KItemImpl
import org.bukkit.entity.Player

class KRowImpl(override val name: String = "") : KRow {

    override val items = mutableMapOf<Int, KItem>()

    private val clickListeners = mutableListOf<KInventory.(Player, KRowImpl, KItem) -> Unit>()
    private var parent: KInventory? = null
    private var index = -1

    override fun onClick(action: KInventory.(player: Player, row: KRowImpl, kItem: KItem) -> Unit) {
        clickListeners += action
    }

    override fun setParent(kInventory: KInventory, index: Int) {
        parent = kInventory
        this.index = index
    }

    override fun getIndex() = index

    override fun setIndex(index: Int) {
        this.index = index
    }

    override fun clicked(player: Player, kItem: KItem) {
        parent?.let { clickListeners.forEach { listener -> listener(it, player, this, kItem) } }
    }

    override fun setItem(slot: Int, item: KItem) {
        if (slot >= 9) error("Slot in a KRow must be between 0-8")

        this.items[slot] = item
        parent?.setRow(index, this)
    }

    override fun setItem(range: IntRange, item: KItem) {
        range.forEach { setItem(it, item ) }
    }
}