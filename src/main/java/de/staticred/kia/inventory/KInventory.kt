package de.staticred.kia.inventory

import de.staticred.kia.animation.Animation
import de.staticred.kia.inventory.item.KItem
import org.bukkit.inventory.Inventory

interface KInventory {

    fun setItem(slot: Int, item: KItem)
    fun setRow(rowIndex: Int, row: KRow)

    fun swapRow(row: KRow, otherRow: KRow)
    fun swapRow(index: Int, otherIndex: Int)

    fun saveRow(row: KRow)
    fun getRow(name: String): KRow?

    fun setOpenAnimation(animation: Animation<KInventory>)
    fun setItemsClickableWhileAnimating(value: Boolean)
    fun isInAnimation(): Boolean

    fun onOpen(action: KInventory.() -> Unit)
    fun onClose(action: KInventory.() -> Unit)

    fun getHolder(): KInventoryHolder
    fun getBukkitInventory(): Inventory

    fun getItems(): Map<Int, KItem>
    fun getRowForItem(item: KItem): KRow?
    fun getSlotForItem(item: KItem): Int

    fun startAnimation(animation: Animation<KInventory>)
    fun isPrivate(): Boolean

    fun opened()
    fun closed()
}