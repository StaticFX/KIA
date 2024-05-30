package de.staticred.kia.inventory.impl

import de.staticred.kia.inventory.KPageController
import de.staticred.kia.inventory.KPageInventory
import de.staticred.kia.inventory.KRow
import de.staticred.kia.inventory.item.RegisteredKItem

class KPageControllerImpl: KPageController {

    override var builder: (nextBtn: RegisteredKItem?, previousBtn: RegisteredKItem?, placeholder: RegisteredKItem?) -> KRow = { _, _, _ -> KRowImpl() }

    override var nextBtn: RegisteredKItem? = null
    override var previousBtn: RegisteredKItem? = null
    override var placeholderItem: RegisteredKItem? = null

    override fun build(): KRow {
        return builder.invoke(nextBtn, previousBtn, placeholderItem)
    }

    fun addButtonListeners() {
        nextBtn?.let { it.onClick { _, _ ->
            if (this !is KPageInventory) error("Next Button in Page Controller used in non KPageInventory")
            this.nextPage()
        }}

        previousBtn?.let { it.onClick { _, _ ->
            if (this !is KPageInventory) { error("Previous Button in Page Controller used in non KPageInventory") }
            this.previousPage()
        }}
    }
}


