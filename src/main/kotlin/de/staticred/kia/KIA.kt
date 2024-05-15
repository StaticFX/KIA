package de.staticred.kia

import de.staticred.kia.events.InventoryClickListener
import de.staticred.kia.events.InventoryDragItemListener
import de.staticred.kia.events.InventoryMoveItemListener
import de.staticred.kia.events.InventoryOpenCloseListener
import de.staticred.kia.example.InventoryExample
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

/**
 * Plugin class of KIA
 */
class KIA: JavaPlugin() {

    companion object {
        lateinit var instance: KIA
    }

    /**
     * Enable function when the plugin starts
     */
    override fun onEnable() {
        instance = this
        hookIntoEvents()
        registerCommands()
    }

    private fun registerCommands() {
        Bukkit.getCommandMap().register("kia", InventoryExample())
    }

    private fun hookIntoEvents() {
        server.pluginManager.registerEvents(InventoryClickListener(), this)
        server.pluginManager.registerEvents(InventoryOpenCloseListener(), this)
        server.pluginManager.registerEvents(InventoryMoveItemListener(), this)
        server.pluginManager.registerEvents(InventoryDragItemListener(), this)
    }


}