package de.staticred.kia.inventory.item

import de.staticred.kia.inventory.AbstractContentContainer
import de.staticred.kia.inventory.KInventory
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack



/**
 * Example implementation of the KItem interface
 * @see KItem
 * @property material of the item
 */
abstract class KItemImpl(override var draggingMode: DraggingMode, val material: Material, amount: Int): ItemStack(material, amount), KItem {

    private val clickListeners = mutableListOf<KInventory.(KItem, Player) -> Unit>()

    override var slot = -1
    override var clickableInAnimation: Boolean = true

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