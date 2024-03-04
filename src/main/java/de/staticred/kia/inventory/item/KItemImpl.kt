package de.staticred.kia.inventory.item

import de.staticred.kia.inventory.KInventory
import de.tr7zw.changeme.nbtapi.NBT
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.UUID

class KItemImpl(private var draggingMode: DraggingMode, val material: Material, amount: Int): ItemStack(material, amount), KItem {

    private val clickListeners = mutableListOf<KInventory.(KItem, Player) -> Unit>()

    val uuid: UUID = ItemManager.generateID()
    var slot = -1

    companion object {
        fun readUUIDFromNBT(item: ItemStack): UUID? {
            return NBT.get(item) {
                if (it.hasTag("UUID"))
                    return@get UUID.fromString(it.getString("UUID"))
                return@get null
            }
        }
    }

    init {
        ItemManager.addItem(this)
        NBT.modify(this) {
            it.setString("UUID", uuid.toString())
        }
    }

    private var parent: KInventory? = null

    override fun onClick(action: KInventory.(KItem, Player) -> Unit) {
        clickListeners += action
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