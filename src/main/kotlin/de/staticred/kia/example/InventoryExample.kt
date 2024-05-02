package de.staticred.kia.example

import de.staticred.kia.inventory.builder.*
import de.staticred.kia.inventory.extensions.openInventory
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

        val pageInventory = kPageInventory(sender) {
            looping = true
            title = Component.text("Paging Inventory")

            mainPage {
                this.title = Component.text("Page 1")
                header = defaultHeader
            }

            openingAnimation = animation(10, 100, TimeUnit.MILLISECONDS) {
                onAnimationFrame {
                    this@kPageInventory.setItem(it, kItem(Material.GOLDEN_APPLE, 1) )
                }
            }

            titleBuilder = { kPage, _ -> run {
                val pageTitle = kPage.title ?: return@run Component.empty()
                val invTitle = this.title ?: return@run Component.empty()

                return@run invTitle.append(Component.text(" - ")).append(pageTitle)
            } }

            addPage {
                this.title = Component.text("Page 2")
                header = defaultHeader
            }

            addPage {
                this.title = Component.text("Page 3")
                header = defaultHeader
            }
        }

        sender.openInventory(pageInventory)
        return true
    }
}