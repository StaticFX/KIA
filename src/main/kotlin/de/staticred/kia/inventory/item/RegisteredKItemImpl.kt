package de.staticred.kia.inventory.item

import de.staticred.kia.KIA
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.util.*

private val nameSpacedKey = NamespacedKey(KIA.instance, "KIA-KItems")

/**
 * Example impl of [RegisteredKItem]
 */
class RegisteredKItemImpl(draggingMode: DraggingMode, material: Material, amount: Int): KItemImpl(draggingMode, material, amount), RegisteredKItem {

    override var id: UUID = ItemManager.generateID()

    companion object {

        /**
         * Read the [UUID] from an item, if it has one
         *
         * @param item [ItemStack]
         * @return [UUID] if the item has one, null otherwise
         */
        fun readUUIDFromNBT(item: ItemStack): UUID? {

            val container = item.itemMeta.persistentDataContainer

            if (container.has(nameSpacedKey)) {
                val uuid = container.get(nameSpacedKey, PersistentDataType.STRING)
                return UUID.fromString(uuid)
            }
            return null
        }
    }

    init {
        if (material != Material.AIR) {
            ItemManager.addItem(this)
            val meta = itemMeta
            meta.persistentDataContainer.set(nameSpacedKey, PersistentDataType.STRING, id.toString())
            super.setItemMeta(meta)
        }
    }

    override fun hasID(): Boolean {
        return true
    }
}