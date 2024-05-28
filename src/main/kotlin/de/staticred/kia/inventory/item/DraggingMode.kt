package de.staticred.kia.inventory.item

/**
 * Models how a KItem can be moved inside a KInventory
 *
 * @see KItem
 * @see de.staticred.kia.inventory.KInventory
 *
 *
 * @author Devin
 * @since 1.0.0
 */
enum class DraggingMode {

    /**
     * Item can't be dragged at all
     */
    NONE,

    /**
     * Item can be dragged globally
     */
    GLOBAL
}