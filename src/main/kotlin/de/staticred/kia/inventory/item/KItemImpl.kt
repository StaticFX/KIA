package de.staticred.kia.inventory.item

import de.staticred.kia.inventory.KInventory
import de.tr7zw.changeme.nbtapi.NBT
import de.tr7zw.changeme.nbtapi.NBTEntity
import de.tr7zw.changeme.nbtapi.NBTItem
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

/**
 * Example implementation of the KItem interface
 * @see KItem
 * @property material of the item
 */
class KItemImpl(private var draggingMode: DraggingMode, val material: Material, amount: Int): ItemStack(material, amount), KItem {

    private val clickListeners = mutableListOf<KInventory.(KItem, Player) -> Unit>()

    val uuid: UUID = ItemManager.generateID()
    override var slot = -1
    override var clickableInAnimation: Boolean = true

    companion object {
        fun readUUIDFromNBT(item: ItemStack): UUID? {
            return NBTItem(item).getUUID("K-UUID")
        }
    }

    init {
        if (material != Material.AIR) {
            ItemManager.addItem(this)
            NBT.modify(this) {
                it.setUUID("K-UUID", uuid)
            }
        }
    }

    private var parent: KInventory? = null

    override fun onClick(action: KInventory.(KItem, Player) -> Unit) {
        clickListeners += action
    }

    override fun enchant(enchantment: Enchantment, level: Int) {
        addEnchantment(enchantment, level)
    }

    override fun setParent(kInventory: KInventory) {
        parent = kInventory
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

    override fun clicked(player: Player) {
        parent?.let { clickListeners.forEach { listener -> listener(it, this, player) } }
    }

    override fun getDraggingMode(): DraggingMode {
        return draggingMode
    }

    override fun setDraggingMode(draggingMode: DraggingMode) {
        this.draggingMode = draggingMode
    }

    override fun uuid(): UUID {
        return uuid
    }

    override fun toItemStack(): ItemStack {
        return this
    }
}