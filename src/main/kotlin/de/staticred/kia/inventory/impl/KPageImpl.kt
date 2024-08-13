package de.staticred.kia.inventory.impl

import de.staticred.kia.animation.Animation
import de.staticred.kia.animation.AnimationManager
import de.staticred.kia.inventory.*
import de.staticred.kia.inventory.builder.kRow
import de.staticred.kia.inventory.events.OpenEventData
import de.staticred.kia.inventory.item.KItem
import de.staticred.kia.inventory.page.AKPage
import de.staticred.kia.util.AIR_ITEM
import de.staticred.kia.util.rows
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

class KPageImpl(override var title: Component?, rowLength: Int = 9, size: Int = 3.rows): AKPage<KPage>(rowLength, size) {
    override var header: KPageController? = null
    override var footer: KPageController? = null

    override val animations: MutableMap<String, Animation<KPage>> = mutableMapOf()
    override var openingAnimation: Animation<KPage>? = null

    override var parent: KPageInventory? = null
    override var currentAnimation: Animation<KPage>? = null


    private val closingListeners = mutableListOf<KPage.(parent: KPageInventory) -> Unit>()

    override fun setItem(slot: Int, value: KItem) {
        val rowOffset = if (hasHeader()) 1 else 0
        super.setItem(slot + (rowLength * rowOffset), value)
        parent?.notifyParent(this)
    }

    override fun setItem(row: Int, slot: Int, item: KItem) {
        val rowOffset = if (hasHeader()) 1 else 0
        setItem(slot + (rowLength * rowOffset), item)
    }

    override fun setRow(index: Int, row: KRow) {
        for (slot in 0 until rowLength) {
            val item = row.items[slot]
            item?.let { setItem(slot + (index * rowLength), it) }
            if (item == null) {
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

            parent = this@KPageImpl
            this.index = index
        }
    }

    override fun hasHeader(): Boolean {
        return header != null
    }

    override fun hasFooter(): Boolean {
        return footer != null
    }

    override fun opened(inventory: KPageInventory, player: Player) {
        super.opened(this, OpenEventData(player))
        parent = inventory
        openingAnimation?.let { AnimationManager.startAnimation(it, this) }
    }

    override fun closed(inventory: KPageInventory) {
        closingListeners.forEach { it(inventory) }
        currentAnimation?.let { AnimationManager.stopAnimation(it) }
    }

    override fun onClosed(action: KPage.(parent: KPageInventory) -> Unit) {
        closingListeners += action
    }

    override fun isAnimating(): Boolean {
        return currentAnimation?.isRunning() ?: false
    }

    override fun addAnimation(identifier: String, animation: Animation<KPage>) {
        animations[identifier] = animation
    }

    override fun startAnimation(animation: Animation<KPage>) {
        if (isAnimating()) error("This page is already inside a animation")

        AnimationManager.startAnimation(animation, this)
        currentAnimation = animation

        animation.onEnd { currentAnimation = null }
    }

    override fun startAnimation(identifier: String) {
        val animation = animations[identifier] ?: error("Animation not found")
        startAnimation(animation)
    }
}