package de.staticred.kia.inventory.item

data class KItemNBTData(
    val id: String,
    val behaviours: Set<String>
)

data class MutableKItemNBTData(
    var id: String,
    var behaviours: MutableSet<String>
) {
    fun toNBTData(): KItemNBTData {
        return KItemNBTData(id, behaviours.toSet())
    }
}
