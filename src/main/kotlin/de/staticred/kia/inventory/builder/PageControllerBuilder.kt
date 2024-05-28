package de.staticred.kia.inventory.builder

import de.staticred.kia.inventory.KPageController
import de.staticred.kia.inventory.impl.KPageControllerImpl

/**
 * Builds a new kPageController
 * @param init function
 * @return newly built kPageController
 *
 * @author Devin
 * @since 1.0.0
 */
fun kPageController(init: KPageController.() -> Unit): KPageController {
    return KPageControllerImpl().apply(init).apply { addButtonListeners() }
}
