package de.staticred.kia.inventory.impl

import de.staticred.kia.inventory.KPageInventory
import de.staticred.kia.inventory.KRow
import de.staticred.kia.inventory.PageController
import de.staticred.kia.inventory.item.KItem

class KPageControllerImpl: PageController {

    override var builder: (nextBtn: KItem?, previousBtn: KItem?, placeholder: KItem?) -> KRow = { _,_,_ -> KRowImpl() }

    override var nextBtn: KItem? = null
    override var previousBtn: KItem? = null
    override var placeholderItem: KItem? = null

    override fun build(): KRow {
        nextBtn?.let { it.onClick { _, _ ->
            if (this !is KPageInventory) error("Next Button in Page Controller used in non KPageInventory")
            this.nextPage()
        } }

        previousBtn?.let { it.onClick { _, _ ->
            if (this !is KPageInventory) { error("Previous Button in Page Controller used in non KPageInventory") }
            this.previousPage()
        }}

        return builder.invoke(nextBtn, previousBtn, placeholderItem)
    }
}


