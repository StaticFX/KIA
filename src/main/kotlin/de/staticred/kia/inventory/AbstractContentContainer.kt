package de.staticred.kia.inventory

import de.staticred.kia.inventory.builder.kRow
import de.staticred.kia.inventory.item.KItem

abstract class AbstractContentContainer(val rowLength: Int) : InventoryContentContainer {

    override val content: MutableMap<Int, KItem> = mutableMapOf()

    override fun setItem(slot: Int, value: KItem) {
        content[slot] = value
    }

    override fun setItem(row: Int, slot: Int, item: KItem) {
        setItem(slot + (row * rowLength), item)
    }

    override fun setRow(index: Int, row: KRow) {
        for ((slot, item) in row.items) {
            setItem(slot + (index * rowLength), item)
        }
    }

    override fun getRowFor(index: Int): KRow {
        return kRow {
            for (slot in 0 until 8) {
                val item = content[slot + (index * 9)]
                item?.let { setItem(slot, it) }
            }
        }
    }

    override fun swapRow(row: KRow, otherRow: KRow) {
        if (row.getIndex() == -1) throw IllegalArgumentException("Row must be set at least once")
        if (otherRow.getIndex() == -1) throw IllegalArgumentException("Row must be set at least once")

        val index = row.getIndex()
        val otherIndex = otherRow.getIndex()

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