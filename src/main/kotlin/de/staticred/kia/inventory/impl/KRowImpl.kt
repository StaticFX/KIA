package de.staticred.kia.inventory.impl

import de.staticred.kia.inventory.KInventory
import de.staticred.kia.inventory.KRow
import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.util.ShiftDirection
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
        this.items[slot] = item
        parent?.setRow(index, this)
    }

    override fun setItem(range: IntRange, item: KItem) {
        range.forEach { setItem(it, item ) }
    }

    override fun shift(direction: ShiftDirection, amount: Int, wrap: Boolean) {
        for ((key, item) in items.toMap()) {
            items.remove(key)
            val newKey = if (direction == ShiftDirection.LEFT) key - amount else key + amount

            if (newKey < 0 && direction == ShiftDirection.LEFT) {
                if (wrap && !(items.any { it.key < newKey })) {
                    items[items.maxOf { it.key } - amount]
                }
            }

            if (newKey < 9 && direction == ShiftDirection.RIGHT) {
                if (wrap && !(items.any { it.key > newKey })) {
                    items[items.minOf { it.key } + amount]
                }
            }

            items[newKey] = item
            parent?.setRow(index, this)
        }
    }
}