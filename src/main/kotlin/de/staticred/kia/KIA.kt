package de.staticred.kia

import de.staticred.kia.events.InventoryClickListener
import de.staticred.kia.events.InventoryDragItemListener
import de.staticred.kia.events.InventoryMoveItemListener
import de.staticred.kia.events.InventoryOpenCloseListener
import de.staticred.kia.example.InventoryExample
import org.bukkit.plugin.java.JavaPlugin

class KIA: JavaPlugin() {

    companion object {
        lateinit var instance: KIA
    }

    override fun onEnable() {
        instance = this
        hookIntoEvents()
        registerCommands()
    }

    private fun registerCommands() {
        getCommand("kia")!!.setExecutor(InventoryExample())
    }


    private fun hookIntoEvents() {
        server.pluginManager.registerEvents(InventoryClickListener(), this)
        server.pluginManager.registerEvents(InventoryOpenCloseListener(), this)
        server.pluginManager.registerEvents(InventoryMoveItemListener(), this)
        server.pluginManager.registerEvents(InventoryDragItemListener(), this)
    }
}