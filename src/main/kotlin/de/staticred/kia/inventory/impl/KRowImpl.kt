package de.staticred.kia.inventory.impl

import de.staticred.kia.inventory.AbstractContentContainer
import de.staticred.kia.inventory.InventoryContentContainer
import de.staticred.kia.inventory.KRow
import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.util.ShiftDirection
import org.bukkit.entity.Player
import kotlin.math.max
import kotlin.math.min

class KRowImpl(override val name: String = "") : KRow {

    override val items = mutableMapOf<Int, KItem>()

    private val clickListeners = mutableListOf<InventoryContentContainer.(Player, KRowImpl, KItem) -> Unit>()
    override var parent: InventoryContentContainer? = null
    override var index = -1

    override fun onClick(action: InventoryContentContainer.(player: Player, row: KRowImpl, kItem: KItem) -> Unit) {
        clickListeners += action
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
        if (parent == null) {
            println("Warning! Tried to shift a row which has a null parent.")
            return
        }
        val rowLength = (parent as AbstractContentContainer).rowLength - 1

        val shift = when (direction) {
            ShiftDirection.LEFT -> -amount
            ShiftDirection.RIGHT -> amount
        }

        val shiftedItems = mutableMapOf<Int, KItem>()

        val indices = items.keys.toIntArray()
        val minIndex = min(indices.min(), 0)
        val maxIndex = max(indices.max(), rowLength)

        for ((index, item) in items) {
            var newIndex = index + shift
            if (!wrap) {
                if (newIndex < minIndex || newIndex > maxIndex) {
                    shiftedItems[newIndex] = item
                    continue
                }
            } else {
                if (newIndex < minIndex) {
                    newIndex += maxIndex - minIndex + 1
                } else if (newIndex > maxIndex) {
                    newIndex -= maxIndex - minIndex + 1
                }
            }
            shiftedItems[newIndex] = item
        }

        items.clear()
        items.putAll(shiftedItems)
    }
}