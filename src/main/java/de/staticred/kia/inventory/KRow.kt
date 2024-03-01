package de.staticred.kia.inventory

import de.staticred.kia.inventory.item.KItem
import org.bukkit.entity.Player

class KRow(val name: String) {

    private val items = mutableMapOf<Int, KItem>()

    private val clickListeners = mutableListOf<KInventory.(Player, KRow) -> Unit>()
    private var parent: KInventory? = null
    private var index = -1

    fun onClick(action: KInventory.(player: Player, row: KRow) -> Unit) {
        clickListeners += action
    }

    fun setParent(kInventory: KInventory, index: Int) {
        parent = kInventory
        this.index = index
    }

    fun getIndex() = index
    fun getItems() = items.toMap()


    fun clicked(player: Player) {
        parent?.let { clickListeners.forEach { listener -> listener(it, player, this) } }
    }

    fun setItem(slot: Int, item: KItem) {
        this.items[slot] = item
    }
}