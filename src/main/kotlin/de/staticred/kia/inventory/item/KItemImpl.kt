package de.staticred.kia.inventory.item

import de.staticred.kia.inventory.AbstractContentContainer
import io.papermc.paper.datacomponent.DataComponentTypes
import net.kyori.adventure.key.Key
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack


/**
 * Example implementation of the KItem interface
 * @see KItem
 * @property material of the item
 */
abstract class KItemImpl(override var draggingMode: DraggingMode, val material: Material, amount: Int): ItemStack(material, amount), KItem {

    override var slot = -1
    override var parent: AbstractContentContainer? = null
    override var model: Key? = null
        set(value) {
            value?.let { setData(DataComponentTypes.ITEM_MODEL, it) }
            field = value
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

    override fun toItemStack(): ItemStack {
        return this
    }
}