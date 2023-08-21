package de.staticred.kia.inventory.impl

import de.staticred.kia.animation.Animation
import de.staticred.kia.animation.AnimationManager
import de.staticred.kia.inventory.*
import de.staticred.kia.inventory.item.KItem
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder

abstract class BaseKInventory(owner: InventoryHolder?): KInventory {

    var size = 3*9
        protected set

    protected var bukkitInventory: Inventory = Bukkit.createInventory(owner, 3*9)

    protected lateinit var holder: KInventoryHolder
    private var itemsClickableWhileAnimating = false

    private val items = mutableMapOf<Int, KItem>()

    private val rows = mutableMapOf<Int, KRow>()
    private val savedRows = mutableMapOf<String, KRow>()

    private var openingAnimation: Animation<KInventory>? = null
    private val animations = mutableListOf<Animation<KInventory>>()

    private val openingListener = mutableListOf<KInventory.() -> Unit>()
    private val closingListener = mutableListOf<KInventory.() -> Unit>()

    override fun setItem(slot: Int, item: KItem) {
        item.setParent(this)
        if (slot > size - 1) throw IllegalArgumentException("Slot must be lower than size. Slot: $slot Size: $size ")
        bukkitInventory.setItem(slot, item)
        items[slot] = item
    }

    override fun setRow(rowIndex: Int, row: KRow) {
        row.setParent(this, rowIndex)
        val items = row.getItems()
        for (i in 0 .. 9) {
            val currItem = items[i]

            if (currItem != null) {
                val slot = i + (rowIndex * 9)
                setItem(slot, currItem)
            }
        }

        rows[rowIndex] = row
        savedRows[row.name] = row
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
        val row = rows[index] ?: throw IllegalArgumentException("$index row not found")
        val otherRow = rows[otherIndex] ?: throw IllegalArgumentException("$index row not found")

        setRow(index, otherRow)
        setRow(otherIndex, row)
    }

    override fun saveRow(row: KRow) {
        savedRows[row.name] = row
    }

    override fun getRow(name: String): KRow? {
        return savedRows[name]
    }

    override fun setOpenAnimation(animation: Animation<KInventory>) {
        openingAnimation = animation
    }

    override fun onOpen(action: KInventory.() -> Unit) {
        openingListener += action
    }

    override fun onClose(action: KInventory.() -> Unit) {
        closingListener += action
    }

    override fun getHolder(): KInventoryHolder {
        return holder
    }

    override fun getBukkitInventory(): Inventory {
        return bukkitInventory
    }

    override fun getItems(): Map<Int, KItem> {
        return items.toMap()
    }

    override fun getRowForItem(item: KItem): KRow? {
        for ((_, value) in rows) {
            if (value.getItems().any { it.value == item }) {
                return value
            }
        }

        return null
    }

    override fun getSlotForItem(item: KItem): Int {
        for ((slot, value) in items) {
            if (value == item) {
                return slot
            }
        }

        return -1
    }

    override fun startAnimation(animation: Animation<KInventory>) {
        animations += animation
        animation.onEnd { animations.remove(animation) }

        AnimationManager.startAnimation(animation, this)
    }

    override fun opened() {
        openingListener.forEach { it(this) }
        openingAnimation?.let {
            AnimationManager.startAnimation(it, this)
        }
    }

    override fun closed() {
        closingListener.forEach { it(this) }
    }

    override fun setItemsClickableWhileAnimating(value: Boolean) {
        itemsClickableWhileAnimating = value
    }

    override fun isInAnimation(): Boolean {
        return animations.any { it.isRunning() }
    }

    abstract override fun isPrivate(): Boolean
}