package de.staticred.kia.inventory

import de.staticred.kia.animation.Animation
import de.staticred.kia.animation.AnimationManager
import de.staticred.kia.inventory.extensions.toKInventory
import de.staticred.kia.inventory.item.KItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder
import org.bukkit.inventory.InventoryView
import java.util.*

/**
 * Example implementation of a generic inventory
 * @param owner holder of the inventory
 */
abstract class BaseKInventory(owner: InventoryHolder?, title: Component?): KInventory {

    var size = 3*9
        protected set

    override var title: Component? = title
        set(newTitle) {
            field = newTitle

            if (newTitle != null)
                this.views.forEach { it.title = LegacyComponentSerializer.legacySection().serialize(newTitle)  }
        }

    override var content: MutableMap<Int, KItem> = mutableMapOf()

    override var views: MutableList<InventoryView> = mutableListOf()
    override var inventories: MutableList<Inventory> = mutableListOf()

    private var isOpen = false
    protected var bukkitInventory: Inventory = if (title == null) Bukkit.createInventory(owner, 3*9, Component.empty()) else Bukkit.createInventory(owner, 3*9, title)

    protected lateinit var holder: KInventoryHolder
    private var itemsClickableWhileAnimating = false

    private var uuid: UUID? = null

    private val rows = mutableMapOf<Int, KRow>()
    private val savedRows = mutableMapOf<String, KRow>()

    override var openingAnimation: Animation<KInventory>? = null
    override var currentAnimation: Animation<KInventory>? = null

    override val animations = mutableMapOf<String, Animation<KInventory>>()
    private val openingListener = mutableListOf<KInventory.() -> Unit>()
    private val closingListener = mutableListOf<KInventory.() -> Unit>()

    override fun setItem(slot: Int, item: KItem) {
        setItemForSlot(slot, item)
    }

    private fun setItemForSlot(slot: Int, item: KItem) {
        item.setParent(this)
        if (slot > size - 1) throw IllegalArgumentException("Slot must be lower than size. Slot: $slot Size: $size ")

        bukkitInventory.setItem(slot, item.toItemStack())
        inventories.forEach { it.setItem(slot, item.toItemStack()) }

        content[slot] = item
    }

    override fun setItem(row: Int, slot: Int, item: KItem) {
        setItemForSlot((9 * row) + slot, item)
    }

    override fun setRow(rowIndex: Int, row: KRow) {
        row.setParent(this, rowIndex)

        for (i in 0 .. 9) {
            val currItem = row.items[i]

            if (currItem != null) {
                val slot = i + (rowIndex * 9)
                setItemForSlot(slot, currItem)
            }
        }

        rows[rowIndex] = row
        savedRows[row.name] = row
    }

    override fun clearInventory() {
        bukkitInventory.clear()
        inventories.forEach { it.clear() }
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


        swapRow(row, otherRow)
    }

    override fun saveRow(row: KRow) {
        savedRows[row.name] = row
    }

    override fun getRow(name: String): KRow? {
        return savedRows[name]
    }

    override fun onOpen(action: KInventory.() -> Unit) {
        openingListener += action
    }

    override fun onClose(action: KInventory.() -> Unit) {
        closingListener += action
    }

    override fun getKHolder(): KInventoryHolder {
        return holder
    }

    override fun toBukkitInventory(): Inventory {
        return bukkitInventory
    }

    override fun getItems(): Map<Int, KItem> {
        return content.toMap()
    }

    override fun getRowForItem(item: KItem): KRow? {
        for ((_, value) in rows) {
            if (value.items.any { it.value == item }) {
                return value
            }
        }

        return null
    }

    override fun getSlotForItem(item: KItem): Int {
        for ((slot, value) in content) {
            if (value == item) {
                return slot
            }
        }

        return -1
    }

    override fun startAnimation(animation: Animation<KInventory>) {
        if (isAnimating()) error("There is already another animation running")
        currentAnimation = animation
        AnimationManager.startAnimation(animation, this)
        animation.onEnd { currentAnimation = null }
    }

    override fun opened() {
        isOpen = true
        openingListener.forEach { it(this) }
        openingAnimation?.let {
            startAnimation(it)
        }

        title?.let { views.forEach { view -> view.title = LegacyComponentSerializer.legacySection().serialize(it) } }
    }

    override fun closed() {
        isOpen = false
        closingListener.forEach { it(this) }
    }

    override fun setItemsClickableWhileAnimating(value: Boolean) {
        itemsClickableWhileAnimating = value
    }

    override fun isAnimating(): Boolean {
        return animations.values.any { it.isRunning() }
    }

    override fun addAnimation(identifier: String, animation: Animation<KInventory>) {
        animations[identifier] = animation
    }

    override fun startAnimation(identifier: String) {
        val animation = animations[identifier] ?: error("Animation not found")
        startAnimation(animation)
    }

    override fun itemsClickableWhileAnimating(): Boolean {
        return itemsClickableWhileAnimating
    }

    override fun isOpened(): Boolean {
        return isOpen
    }

    abstract override fun isPrivate(): Boolean


    override fun setID(id: UUID) {
        this.uuid = id
    }

    override fun getID(): UUID? {
        return this.uuid
    }

    override fun hasID(): Boolean {
        return uuid != null
    }

    override fun isEqual(inventory: Inventory): Boolean {
        if (!hasID()) return false
        val oKInventory = inventory.toKInventory() ?: return false
        if (!oKInventory.hasID()) return false

       return oKInventory.getID()!! == uuid
    }
}