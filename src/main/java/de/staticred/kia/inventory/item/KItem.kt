package de.staticred.kia.inventory.item

import de.staticred.kia.inventory.KInventory
import de.tr7zw.changeme.nbtapi.NBT
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

class KItem(val draggable: Boolean, val material: Material, amount: Int): ItemStack(material, amount) {

    private val clickListeners = mutableListOf<KInventory.(Player) -> Unit>()

    val uuid: UUID = ItemManager.generateID()
    var slot = -1

    init {
        ItemManager.addItem(this)
        NBT.modify(this) {
            it.setString("UUID", uuid.toString())
        }
    }

    private var parent: KInventory? = null

    fun onClick(action: KInventory.(Player) -> Unit) {
        clickListeners += action
    }

    fun setParent(kInventory: KInventory) {
        parent = kInventory
    }
    
    fun setDisplayName(name: Component) {
        val itemMeta = itemMeta
        itemMeta.displayName(name)
        setItemMeta(itemMeta)
    }

    fun setItemLore(lore: List<Component>) {
        val itemMeta = itemMeta
        itemMeta.lore(lore)
        setItemMeta(itemMeta)
    }

    fun clicked(player: Player) {
        parent?.let { clickListeners.forEach { listener -> listener(it, player) } }
    }


}