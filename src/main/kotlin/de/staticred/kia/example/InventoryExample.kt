package de.staticred.kia.example

import de.staticred.kia.animation.Animation
import de.staticred.kia.inventory.builder.InventoryBuilder
import de.staticred.kia.inventory.KInventory
import de.staticred.kia.inventory.impl.KRowImpl
import de.staticred.kia.inventory.extensions.openInventory
import de.staticred.kia.inventory.item.DraggingMode
import de.staticred.kia.inventory.item.KItemImpl
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

class InventoryExample: CommandExecutor {


    private val spiral = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 17, 26, 25, 24, 23, 22, 21, 20, 19, 18, 9, 10, 11, 12, 13, 14, 15, 16)

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {

        if (sender !is Player) return false

        val inventory = InventoryBuilder(sender)
            .setPrivate()
            .setSize(3*9)
            .setTitle(Component.text("Example inventory"))
            .build()

        val openingAnimation = Animation<KInventory>(27, 30, TimeUnit.MILLISECONDS)
        val item = KItemImpl(DraggingMode.NONE, Material.BLACK_STAINED_GLASS_PANE, 1)

        item.setDisplayName(Component.text("§8Placeholder"))
        item.onClick { clickedItem, player -> run {
            player.sendMessage(Component.text(clickedItem.slot))
            }
        }

        val placeholderRow = KRowImpl("placeholder")
        for (i in 0..8) {
            placeholderRow.setItem(i, item)
        }

        openingAnimation.onAnimationFrame {
            this.setItem(spiral[it], item)
        }

        val clickItem = KItemImpl(DraggingMode.NONE, Material.COMPASS, 1)

        clickItem.onClick { kItem, player -> run { player.sendMessage(Component.text("Idiot")) } }

        openingAnimation.onEnd {
            inventory.setItem(15, clickItem)
        }

        placeholderRow.onClick { player, row, clickedItem
            -> run { player.sendMessage(Component.text("You clicked row: ${row.name} - ${row.getIndex()} item: ${clickedItem.slot}")) } }

        inventory.setOpenAnimation(openingAnimation)

        sender.openInventory(inventory)
        return true
    }
}