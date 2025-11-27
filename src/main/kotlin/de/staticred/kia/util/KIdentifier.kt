package de.staticred.kia.util

import org.bukkit.plugin.Plugin

/**
 * Represents a unique identifier specific to a plugin.
 *
 * @property plugin The plugin associated with this identifier.
 * @property identifier The unique identifier string within the scope of the plugin.
 */
data class KIdentifier(val plugin: Plugin, val identifier: String) {
    fun build() = "${plugin.name}:$identifier"

    override fun toString(): String {
        return build()
    }

}
