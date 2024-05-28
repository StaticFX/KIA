package de.staticred.kia.example

import de.staticred.kia.inventory.builder.kInventory
import de.staticred.kia.inventory.builder.kItem
import de.staticred.kia.inventory.extensions.openInventory
import de.staticred.kia.inventory.item.DraggingMode
import de.staticred.kia.util.rows
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType

class InventoryExample: Command("kia") {


    override fun execute(sender: CommandSender, p1: String, p2: Array<out String>?): Boolean {
        if (sender !is Player) return false

        val inventory = kInventory(sender, 5.rows, InventoryType.CHEST) {
            title = Component.text("This is working")
            for (i in 0..8) {
                setItem(i, kItem(Material.DIAMOND) {
                    draggingMode = DraggingMode.GLOBAL
                    onClick { kItem, player ->
                        player.sendMessage(Component.text(kItem.uuid.toString()))
                    }
                } )
            }
        }

        sender.openInventory(inventory)
        return true
    }
}