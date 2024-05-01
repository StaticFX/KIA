package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.KPage
import de.staticred.kia.inventory.PageController
import de.staticred.kia.inventory.impl.KPageImpl
import de.staticred.kia.inventory.item.KItem
import net.kyori.adventure.text.Component

class PageBuilder {

    private var header: PageController? = null
    private var footer: PageController? = null
    private var title: Component? = null
    var content =  mutableMapOf<Int, KItem>()

    fun setHeader(header: PageController): PageBuilder {
        this.header = header
        return this
    }

    fun setFooter(footer: PageController): PageBuilder {
        this.footer = footer
        return this
    }

    fun setItem(slot: Int, item: KItem): PageBuilder {
        content[slot] = item
        return this
    }

    fun setTitle(title: Component?): PageBuilder {
        this.title = title
        return this
    }

    fun build(): KPage {
        return KPageImpl(title).apply {
            footer = this@PageBuilder.footer
            header = this@PageBuilder.header
            content = this@PageBuilder.content
        }
    }
}