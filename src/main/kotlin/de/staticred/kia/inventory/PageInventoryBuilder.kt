package de.staticred.kia.inventory

import de.staticred.kia.inventory.impl.PageKInventoryImpl
import org.bukkit.entity.Player

class PageInventoryBuilder(private val holder: Player, val mainPage: KPage): InventoryBuilder(holder) {

    var looping = false
    var pages = mutableListOf<KPage>()

    fun setLooping(looping: Boolean): PageInventoryBuilder {
        this.looping = looping
        return this
    }

    fun addPage(page: KPage): PageInventoryBuilder {
        this.pages += page
        return this
    }

    override fun build(): KInventory {
        val inventory = PageKInventoryImpl(mainPage, KInventoryHolder.create(holder), looping)
        pages.forEach { inventory.addPage(it) }

        return inventory
    }
}