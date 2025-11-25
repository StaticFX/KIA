package de.staticred.kia.example

import de.staticred.kia.inventory.builder.animation
import de.staticred.kia.inventory.builder.kInventory
import de.staticred.kia.inventory.builder.kItem
import de.staticred.kia.inventory.builder.kModel
import de.staticred.kia.inventory.extensions.openInventory
import de.staticred.kia.inventory.extensions.setHotbarItem
import de.staticred.kia.inventory.item.KItemModel
import de.staticred.kia.util.rows
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryType
import java.util.concurrent.TimeUnit

class InventoryExample : Command("kia") {
    override fun execute(sender: CommandSender, command: String, args: Array<String>): Boolean {
        if (sender !is Player) return false
        val miniMessage = MiniMessage.miniMessage()

        val text =
            miniMessage
                .deserialize("<dark_gray><st>---------------------------------</st></dark_gray> ")
                .append { miniMessage.deserialize("\n<gradient:#B125EA:#7F52FF>KIA - Kotlin Inventory API</gradient>") }
                .append { miniMessage.deserialize("\n<color:#E24462>GitHub: https://github.com/StaticFX/KIA </color>") }
                .append { miniMessage.deserialize("\n<color:#E24462>By: StaticFX / Devin </color>") }
                .append {
                    miniMessage.deserialize(
                        "\n<color:#E24462><underlined>Click to open example inventory</underlined></color>",
                    )
                }.clickEvent(
                    ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/kia inv"),
                ).append { miniMessage.deserialize("\n<dark_gray><st>---------------------------------</st></dark_gray>") }

        val inventory =
            kInventory(sender, 3.rows, InventoryType.CHEST) {
                title = miniMessage.deserialize("<gradient:#B125EA:#7F52FF>KIA - Kotlin Inventory API</gradient>")

                openingAnimation =
                    animation(27, 50, TimeUnit.MILLISECONDS) {
                        onAnimationFrame { frame ->
                            setItem(
                                frame,
                                kItem(Material.GRAY_STAINED_GLASS_PANE) {
                                    setDisplayName(miniMessage.deserialize("<black>|</black>"))
                                },
                            )
                        }

                        onEnd {
                            setItem(
                                1,
                                4,
                                kItem(Material.PAPER) {
                                    setDisplayName(miniMessage.deserialize("<rainbow:!>Thanks for using KIA!</rainbow>"))
                                },
                            )
                            val namespacedKey = NamespacedKey.fromString("kia:dice")
                            namespacedKey?.let {
                                setItem(2, 4,
                                    kItem(Material.PAPER) {
                                        setDisplayName(miniMessage.deserialize("<rainbow:!>Here is an item with custom model</rainbow>"))
                                        model = kModel(NamespacedKey.minecraft("kia:item"))
                                    }
                                )
                            }
                        }
                    }
            }

        if (args.isNotEmpty()) {
            if (args[0] == "inv") {
                sender.openInventory(inventory)
            } else if (args[0] == "items") {
                val item =
                    kItem(Material.PAPER) {
                        setDisplayName(Component.text("Left or right click me"))
                        onLeftClick { clicker, _ ->
                            clicker.sendMessage("You left clicked")
                        }

                        onRightClick { clicker, _ ->
                            clicker.sendMessage("You right clicked")
                        }
                    }

                sender.setHotbarItem(0, item)
            }
        } else {
            sender.sendMessage(text)
        }

        return true
    }
}
