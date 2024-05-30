package de.staticred.kia.inventory.item

import de.staticred.kia.KIA
import de.staticred.kia.animation.Animatable
import de.staticred.kia.inventory.KInventory
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.util.*

private val nameSpacedKey = NamespacedKey(KIA.instance, "KIA-KItems")

/**
 * Example impl of [RegisteredKItem]
 */
class RegisteredKItemImpl(draggingMode: DraggingMode, material: Material, amount: Int): KItemImpl(draggingMode, material, amount), RegisteredKItem {
    override var id: UUID = ItemManager.generateID()
    override var clickableInAnimation: Boolean = true

    private val clickListeners = mutableListOf<KInventory.(RegisteredKItem, Player) -> Unit>()


    companion object {
        /**
         * Reads the [UUID] from an item, if it has one
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

    override fun onClick(action: KInventory.(RegisteredKItem, Player) -> Unit) {
        clickListeners += action
    }

    override fun clicked(player: Player, kInventory: KInventory) {
        val currentParent = parent

        if (!clickableInAnimation && currentParent != null) {
            if (currentParent is Animatable<*> && currentParent.isAnimating()) return
        }

        parent?.let { clickListeners.forEach { listener -> listener(kInventory, this, player) } }
    }

    override fun hasID(): Boolean {
        return true
    }
}