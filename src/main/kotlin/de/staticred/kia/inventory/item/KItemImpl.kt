package de.staticred.kia.inventory.item

import de.staticred.kia.KIA
import de.staticred.kia.inventory.AbstractContentContainer
import de.staticred.kia.inventory.KInventory
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType
import java.util.*


private val nameSpacedKey = NamespacedKey(KIA.instance, "KIA-KItems")

/**
 * Example implementation of the KItem interface
 * @see KItem
 * @property material of the item
 */
class KItemImpl(override var draggingMode: DraggingMode, val material: Material, amount: Int): ItemStack(material, amount), KItem {

    private val clickListeners = mutableListOf<KInventory.(KItem, Player) -> Unit>()

    override val uuid: UUID = ItemManager.generateID()
    override var slot = -1
    override var clickableInAnimation: Boolean = true

    companion object {
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
            meta.persistentDataContainer.set(nameSpacedKey, PersistentDataType.STRING, uuid.toString())
            super.setItemMeta(meta)
        }
    }

    override var parent: AbstractContentContainer? = null

    override fun onClick(action: KInventory.(KItem, Player) -> Unit) {
        clickListeners += action
    }

    override fun enchant(enchantment: Enchantment, level: Int) {
        addEnchantment(enchantment, level)
    }

    override fun draggable(): Boolean {
        return draggingMode != DraggingMode.NONE
    }

    override fun setDisplayName(name: Component) {
        val itemMeta = itemMeta
        itemMeta.displayName(name)
        setItemMeta(itemMeta)
    }

    override fun setItemLore(lore: List<Component>) {
        val itemMeta = itemMeta
        itemMeta.lore(lore)
        setItemMeta(itemMeta)
    }

    override fun clicked(player: Player, kInventory: KInventory) {
        parent?.let { clickListeners.forEach { listener -> listener(kInventory, this, player) } }
    }

    override fun toItemStack(): ItemStack {
        return this
    }
}