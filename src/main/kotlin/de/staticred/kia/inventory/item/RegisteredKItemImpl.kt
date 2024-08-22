package de.staticred.kia.inventory.item

import de.staticred.kia.KIA
import de.staticred.kia.animation.Animatable
import de.staticred.kia.inventory.KInventory
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.util.*

private val nameSpacedKey = NamespacedKey(KIA.plugin, "KIA-KItems")

/**
 * Example impl of [RegisteredKItem]
 */
class RegisteredKItemImpl(
    draggingMode: DraggingMode,
    material: Material,
    amount: Int,
) : KItemImpl(draggingMode, material, amount),
    RegisteredKItem {
    override var id: UUID = ItemManager.generateID()
    override var clickableInAnimation: Boolean = true

    private val clickListeners = mutableListOf<KInventory.(RegisteredKItem, Player) -> Unit>()
    private val leftClickListeners = mutableListOf<RegisteredKItem.(Player, PlayerInteractEvent) -> Unit>()
    private val rightClickListeners = mutableListOf<RegisteredKItem.(Player, PlayerInteractEvent) -> Unit>()

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

    override fun clicked(
        player: Player,
        kInventory: KInventory,
    ) {
        val currentParent = parent

        if (!clickableInAnimation && currentParent != null) {
            if (currentParent is Animatable<*> && currentParent.isAnimating()) return
        }

        parent?.let { clickListeners.forEach { listener -> listener(kInventory, this, player) } }
    }

    override fun onLeftClick(action: RegisteredKItem.(Player, PlayerInteractEvent) -> Unit) {
        leftClickListeners += action
    }

    override fun onRightClick(action: RegisteredKItem.(Player, PlayerInteractEvent) -> Unit) {
        rightClickListeners += action
    }

    override fun leftClicked(
        player: Player,
        event: PlayerInteractEvent,
    ) {
        leftClickListeners.forEach { it(this, player, event) }
    }

    override fun rightClicked(
        player: Player,
        event: PlayerInteractEvent,
    ) {
        rightClickListeners.forEach { it(this, player, event) }
    }

    override fun hasID(): Boolean = true
}
