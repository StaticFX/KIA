package de.staticred.kia.inventory

import de.staticred.kia.inventory.builder.kRow
import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.util.AIR_ITEM

/**
 * Basic representation of a content container with simple item managements
 * @see InventoryContentContainer
 * @property rowLength the length of a row in the inventory
 */
abstract class AbstractContentContainer(val rowLength: Int) : InventoryContentContainer {

    override val content: MutableMap<Int, KItem> = mutableMapOf()

    override fun setItem(slot: Int, value: KItem) {
        content[slot] = value
    }

    override fun setItem(row: Int, slot: Int, item: KItem) {
        setItem(slot + (row * rowLength), item)
    }

    override fun setRow(index: Int, row: KRow) {
        for (slot in 0 until rowLength) {
            row.items[slot]?.let { setItem(slot + (index * rowLength), it) }
            if (row.items[slot] == null) {
                setItem(slot + (index * rowLength), AIR_ITEM)
            }
        }

        row.parent = this
        row.index = index
    }

    override fun getRowFor(index: Int): KRow {
        return kRow {
            for (slot in 0 .. 8) {
                val item = content[slot + (index * 9)]
                item?.let { setItem(slot, it) }
            }
        }
    }

    override fun swapRow(row: KRow, otherRow: KRow) {
        if (row.index == -1) throw IllegalArgumentException("Row must be set at least once")
        if (otherRow.index == -1) throw IllegalArgumentException("Row must be set at least once")

        val index = row.index
        val otherIndex = otherRow.index

        setRow(otherIndex, row)
        setRow(index, otherRow)
    }

    override fun swapRow(index: Int, otherIndex: Int) {
        swapRow(getRowFor(index), getRowFor(otherIndex))
    }

    override fun clearInventory() {
        content.clear()
    }
}