package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.PageController
import de.staticred.kia.inventory.impl.KPageControllerImpl


fun kPageController(init: PageController.() -> Unit): PageController {
    return KPageControllerImpl().apply(init).apply { addButtonListeners() }
}

