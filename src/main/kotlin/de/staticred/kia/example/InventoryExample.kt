package de.staticred.kia.example

import de.staticred.kia.animation.Animation
import de.staticred.kia.inventory.KPageInventory
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

    private val spiral = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 17, 26, 25, 24, 23, 22, 21, 20, 19, 18, 9, 10, 11, 12, 13, 14, 15, 16)

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>?): Boolean {

        if (sender !is Player) return false

        val pageInventory = kPageInventory(sender) {
            mainPage {
                this.title = Component.text("Main page")
                setItem(1, kItem(Material.STONE, 1) )

                header = kPageController {
                    nextBtn = kItem(Material.SPRUCE_SIGN, 1) {
                        setDisplayName(Component.text("Next Page ->"))
                    }
                    previousBtn = kItem(Material.SPRUCE_SIGN, 1) {
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
                    } }
                }
            }

            titleBuilder = { kPage, _ -> run {
                val pageTitle = kPage.title ?: return@run Component.empty()
                val invTitle = this.title ?: return@run Component.empty()

                return@run invTitle.append(Component.text(" - ")).append(pageTitle)
            } }

            this.addPage(kPage {
                this.title = Component.text("Page")
                header = kPageController {
                    this.nextBtn = kItem(Material.SPRUCE_SIGN, 1) {}
                    builder = { nextBtn, prevBtn, _ -> run { kRow {
                        setItem(3, nextBtn!!)
                    } } }
                }
            })
        }

        sender.openInventory(pageInventory)

        return true
    }
}