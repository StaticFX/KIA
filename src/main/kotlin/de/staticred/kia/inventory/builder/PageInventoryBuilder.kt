package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.KInventory
import de.staticred.kia.inventory.KInventoryHolder
import de.staticred.kia.inventory.KPage
import de.staticred.kia.inventory.impl.PageKInventoryImpl
import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

class PageInventoryBuilder(private val holder: Player, val mainPage: KPage): InventoryBuilder(holder) {

    private var titleBuilder: ((inventory: KInventory, currentPage: KPage) -> Component)? = null
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

    fun setTitleBuilder(builder: (inventory: KInventory, currentPage: KPage) -> Component): PageInventoryBuilder {
        this.titleBuilder = builder
        return this
    }

    override fun build(): KInventory {
        val inventory = PageKInventoryImpl(mainPage, KInventoryHolder.create(holder), looping)


        pages.forEach { inventory.addPage(it) }

        return inventory
    }
}