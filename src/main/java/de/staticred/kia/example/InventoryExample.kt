package de.staticred.kia.example

import de.staticred.kia.animation.Animation
import de.staticred.kia.inventory.InventoryBuilder
import de.staticred.kia.inventory.KInventory
import de.staticred.kia.inventory.KRow
import de.staticred.kia.inventory.item.KItem
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

class InventoryExample: CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {

        if (sender !is Player) return false

        val inventory = InventoryBuilder(sender)
            .setPrivate()
            .setSize(3*9)
            .setTitle(Component.text("Example inventory"))
            .build()

        val openingAnimation = Animation<KInventory>(3, 500, TimeUnit.MILLISECONDS)
        val item = KItem(false, Material.BLACK_STAINED_GLASS_PANE, 1)
        item.setDisplayName(Component.text("§8Placeholder"))
        item.onClick {
            it.sendMessage(Component.text(item.slot))
        }

        val placeholderRow = KRow("placeholder")
        for (i in 0..8) {
            placeholderRow.setItem(i, item)
        }

        openingAnimation.onAnimationFrame {
            this.setRow(it, placeholderRow)
        }


        placeholderRow.onClick {
            it.sendMessage(Component.text("Funktioniert"))
        }

        inventory.setOpenAnimation(openingAnimation)

        sender.openInventory(inventory.getBukkitInventory())
        return true
    }
}