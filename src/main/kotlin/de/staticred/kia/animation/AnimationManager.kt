package de.staticred.kia.animation

import de.staticred.kia.KIA
import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import org.bukkit.Bukkit

object AnimationManager {

    private val runningAnimations = mutableMapOf<ScheduledTask, AnimationImpl<*>>()

    fun <T> startAnimation(animation: Animation<T>, t: T) {
        if (animation !is AnimationImpl<T>) {
            error("Animation must be type of AnimationImpl")
        }

        animation.startAnimation()
        val task = Bukkit.getAsyncScheduler().runAtFixedRate(KIA.instance, {
                animation.renderFrame(t)
        }, 0, animation.interval, animation.timeUnit)
        runningAnimations[task] = animation
    }

    fun <T> stopAnimation(animation: AnimationImpl<T>) {
        for ((key, value) in runningAnimations) {
            if (value == animation) {
                key.cancel()
            }
        }
    }
}