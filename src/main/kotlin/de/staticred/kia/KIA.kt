package de.staticred.kia

import de.staticred.kia.events.InventoryClickListener
import de.staticred.kia.events.InventoryDragItemListener
import de.staticred.kia.events.InventoryOpenCloseListener
import de.staticred.kia.events.PlayerInteractListener
import de.staticred.kia.example.InventoryExample
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

/**
 * Plugin class of KIA
 */
object KIA {
    /**
     * The paper plugin kia is attached to
     */
    lateinit var plugin: JavaPlugin
        private set

    /**
     * Create a new loaded instance of KIA
     * @param javaPlugin plugin instance KIA will use to attach its eventlisteners
     * @param exampleCommand by default false. If true, will register the /kia example command
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
        plugin.server.pluginManager.registerEvents(PlayerInteractListener(), plugin)
    }
}
