package de.staticred.kia

import de.staticred.kia.events.InventoryClickListener
import de.staticred.kia.events.InventoryDragItemListener
import de.staticred.kia.events.InventoryOpenCloseListener
import de.staticred.kia.example.InventoryExample
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

/**
 * Plugin class of KIA
 */
object KIA {
    lateinit var plugin: JavaPlugin
        private set

    /**
     * Create new loaded instance of KIA
     */
    fun create(
        javaPlugin: JavaPlugin,
        exampleCommand: Boolean = false,
    ) {
        plugin = javaPlugin
        hookIntoEvents()

        if (exampleCommand) {
            registerCommands()
        }
    }

    private fun registerCommands() {
        Bukkit.getCommandMap().register("kia", InventoryExample())
    }

    private fun hookIntoEvents() {
        plugin.server.pluginManager.registerEvents(InventoryClickListener(), plugin)
        plugin.server.pluginManager.registerEvents(InventoryOpenCloseListener(), plugin)
        plugin.server.pluginManager.registerEvents(InventoryDragItemListener(), plugin)
    }
}
