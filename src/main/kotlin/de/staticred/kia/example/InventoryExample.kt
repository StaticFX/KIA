package de.staticred.kia.example

import de.staticred.kia.inventory.builder.*
import de.staticred.kia.inventory.extensions.openInventory
import de.staticred.kia.util.ShiftDirection
import de.staticred.kia.util.rows
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import java.util.concurrent.TimeUnit

class InventoryExample: Command("kia") {


    override fun execute(sender: CommandSender, p1: String, p2: Array<out String>?): Boolean {
        if (sender !is Player) return false


        val inventory = kInventory(sender, 5.rows, InventoryType.CHEST) {
            setItem(1, 4, kItem(Material.DIAMOND_PICKAXE) {
                onClick { kItem, player -> player.sendMessage("Cool you just clicked ${kItem.slot}") }
            })
        }

        sender.openInventory(inventory)

        val defaultHeader = kPageController {
            nextBtn = kItem(Material.PAPER, 1) {
                setDisplayName(Component.text("Next Page ->"))
            }
            previousBtn = kItem(Material.PAPER, 1) {
                setDisplayName(Component.text("<- Previous Page"))
            }
            placeholderItem = kItem(Material.BLACK_STAINED_GLASS_PANE, 1) {
                setDisplayName(Component.text("'"))
            }
            builder = { nextBtn, previousBtn, placeholder -> run {
                kRow {
                    setItem(0..1, placeholder!!)
                    setItem(2, previousBtn!!)
                    setItem(3..5, placeholder)
                    setItem(6, nextBtn!!)
                    setItem(7..8, placeholder)
                }
            }}
        }

        val pageInventory = kPageInventory(sender, 5.rows) {
            looping = true
            title = Component.text("Paging Inventory")

            mainPage {
                this.title = Component.text("This is the main page")
                header = defaultHeader
            }

            addPage {
                this.title = Component.text("Page 2")
                header = defaultHeader
            }

            addPage {
                this.title = Component.text("Page 3")
                header = defaultHeader
            }
        }

        return true
    }
}