package de.staticred.kia.example

import de.staticred.kia.inventory.builder.*
import de.staticred.kia.inventory.extensions.openInventory
import de.staticred.kia.util.ShiftDirection
import de.staticred.kia.util.rows
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

class InventoryExample: Command("kia") {


    override fun execute(sender: CommandSender, p1: String, p2: Array<out String>?): Boolean {
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

        val pageInventory = kPageInventory(sender, 5.rows) {
            looping = true
            title = Component.text("Paging Inventory")

            mainPage {
                this.title = Component.text("Page 1")

                val shiftingRow = kRow("Shifting Row") {
                    for (slot in 0..8) {
                        if (slot % 2 == 0) {
                            setItem(slot, kItem(Material.BLACK_STAINED_GLASS_PANE) {})
                        } else {
                            setItem(slot, kItem(Material.WHITE_STAINED_GLASS_PANE) {})
                        }
                    }
                }

                setRow(1, shiftingRow)

                openingAnimation = animation(3, 1, TimeUnit.SECONDS) {
                    onAnimationFrame {
                        setRow(1, getRowFor(1).apply { shift(ShiftDirection.LEFT, 1, false) })
                    }
                }
            }

            titleBuilder = { kPage, _ -> run {
                val pageTitle = kPage.title ?: return@run Component.empty()
                val invTitle = this.title ?: return@run Component.empty()

                return@run invTitle.append(Component.text(" - ")).append(pageTitle)
            } }

            addStaticPage("Games") {
                title = Component.text("Games")
                this.setItem(1, 1, kItem(Material.DIAMOND_PICKAXE, 1) {
                    setDisplayName(Component.text("Next Page -> "))
                })
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

        sender.openInventory(pageInventory)
        return true
    }
}