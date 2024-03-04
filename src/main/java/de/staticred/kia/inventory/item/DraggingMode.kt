package de.staticred.kia.inventory.item

/**
 * Models how a KItem can be moved inside a KInventory
 *
 * @see KItem
 * @see KInvetory
 *
 */
enum class DraggingMode {

    /**
     * Item can't be dragged at all
     */
    NONE,

    /**
     * Item can be dragged inside the KInventory
     */
    IN_INVENTORY,

    /**
     * Item can be dragged inside the row
     */
    IN_ROW,

    /**
     * Item can be dragged globally
     */
    GLOBAL
}