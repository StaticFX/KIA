package de.staticred.kia.inventory.impl

import KPageController
import de.staticred.kia.animation.Animation
import de.staticred.kia.animation.AnimationManager
import de.staticred.kia.inventory.KPage
import de.staticred.kia.inventory.KPageInventory
import de.staticred.kia.inventory.KRow
import de.staticred.kia.inventory.item.KItem
import net.kyori.adventure.text.Component

class KPageImpl(override var title: Component?): KPage {
    override var header: KPageController? = null
    override var footer: KPageController? = null
    override var content: MutableMap<Int, KItem> = mutableMapOf()

    override val animations: MutableMap<String, Animation<KPage>> = mutableMapOf()
    override var openingAnimation: Animation<KPage>? = null

    override var parent: KPageInventory? = null
    override var currentAnimation: Animation<KPage>? = null

    private val openingListeners = mutableListOf<KPage.(parent: KPageInventory) -> Unit>()
    private val closingListeners = mutableListOf<KPage.(parent: KPageInventory) -> Unit>()

    override fun setItem(slot: Int, item: KItem) {
        content[slot] = item
    }

    override fun setItem(row: Int, slot: Int, item: KItem) {
        val rowOffset = if (hasHeader()) 1 else 0
        setItem(slot + (9 * rowOffset), item)
    }

    override fun setRow(index: Int, row: KRow) {
        for ((slot, item) in row.items) {
            content[slot + (index * 9)] = item
        }

        parent?.let { row.setParent(it, index) }
    }

    override fun hasHeader(): Boolean {
        return header != null
    }

    override fun hasFooter(): Boolean {
        return footer != null
    }

    override fun opened(inventory: KPageInventory) {
        openingListeners.forEach { it(inventory) }
        openingAnimation?.let { AnimationManager.startAnimation(it, this) }
    }

    override fun closed(inventory: KPageInventory) {
        closingListeners.forEach { it(inventory) }
        currentAnimation?.let { AnimationManager.stopAnimation(it) }
    }

    override fun onOpened(action: KPage.(parent: KPageInventory) -> Unit) {
        openingListeners += action
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