package de.staticred.kia.inventory.item

import de.staticred.kia.KIA
import de.staticred.kia.animation.Animatable
import de.staticred.kia.behaviour.item.KItemBehavior
import de.staticred.kia.behaviour.item.KItemBehaviorContext
import de.staticred.kia.inventory.KInventory
import de.staticred.kia.util.KIdentifier
import de.staticred.kia.util.RuntimeFunction
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.util.*

private val itemNamespace = NamespacedKey(KIA.plugin, "items")

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

    /**
     * Runtime listeners
     */
    private val clickListeners = mutableListOf<KInventory.(RegisteredKItem, Player) -> Unit>()
    private val leftClickListeners = mutableListOf<RegisteredKItem.(Player, PlayerInteractEvent) -> Unit>()
    private val rightClickListeners = mutableListOf<RegisteredKItem.(Player, PlayerInteractEvent) -> Unit>()

    private val nbtData = MutableKItemNBTData(id.toString(), mutableSetOf())

    companion object {
        /**
         * Reads the [UUID] from an item, if it has one
         *
         * @param item [ItemStack]
         * @return [UUID] if the item has one, null otherwise
         */
        fun readUUIDFromNBT(item: ItemStack): UUID? {
            val container = item.itemMeta.persistentDataContainer

            if (container.has(itemNamespace)) {
                val uuid = container.get(itemNamespace, PersistentDataType.STRING)
                val nbtData = KIA.gson.fromJson(uuid, KItemNBTData::class.java)
                return UUID.fromString(nbtData.id)
            }
            return null
        }

        fun readNBTDataFromItem(item: ItemStack): KItemNBTData? {
            val container = item.itemMeta.persistentDataContainer

            if (container.has(itemNamespace)) {
                val uuid = container.get(itemNamespace, PersistentDataType.STRING)
                return KIA.gson.fromJson(uuid, KItemNBTData::class.java)
            }
            return null
        }
    }

    init {
        if (material != Material.AIR) {
            ItemManager.addItem(this)
            updateNBT()
        }
    }

    private fun updateNBT() {
        val json = buildJsonNBT()
        editPersistentDataContainer { it.set(itemNamespace, PersistentDataType.STRING, json) }
    }

    private fun buildJsonNBT(): String {
        return KIA.gson.toJson(nbtData.toNBTData())
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

    @RuntimeFunction
    override fun onLeftClick(action: RegisteredKItem.(Player, PlayerInteractEvent) -> Unit) {
        leftClickListeners += action
    }

    @RuntimeFunction
    override fun onRightClick(action: RegisteredKItem.(Player, PlayerInteractEvent) -> Unit) {
        rightClickListeners += action
    }

   override fun addBehavior(identifier: KIdentifier, block: ItemStack.(context: KItemBehaviorContext) -> Unit): KItemBehavior {
        val behavior =  KItemBehavior(identifier).apply {
            behave(block)
        }

       nbtData.behaviours += identifier.toString()
       updateNBT()
       return behavior
    }

    override fun addBehavior(kItemBehavior: KItemBehavior): KItemBehavior {
        nbtData.behaviours += kItemBehavior.identifier.toString()
        updateNBT()
        return kItemBehavior
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
