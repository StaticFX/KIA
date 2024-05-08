package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.KPageController
import de.staticred.kia.inventory.impl.KPageControllerImpl


fun kPageController(init: KPageController.() -> Unit): KPageController {
    return KPageControllerImpl().apply(init).apply { addButtonListeners() }
}

