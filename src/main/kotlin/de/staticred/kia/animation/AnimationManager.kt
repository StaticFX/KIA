package de.staticred.kia.animation

import de.staticred.kia.KIA
import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import org.bukkit.Bukkit

/**
 * Util class to handle animation across classes
 *
 * @since 1.0.0
 */
object AnimationManager {
    private val runningAnimations = mutableMapOf<ScheduledTask, AnimationImpl<*>>()

    /**
     * Starts the given animation, on the given class
     *
     * Animation will run in an async bukkit task
     *
     * @param animation the animation to start
     * @param t the instance to run it on
     *
     * @see Animation.onAnimationFrame
     *
     * @warn use AnimationImpl as the animation when not built using the animation builder
     * @see AnimationImpl
     * @see de.staticred.kia.inventory.builder.animation
     */
    fun <T> startAnimation(
        animation: Animation<T>,
        t: T,
    ) {
        if (animation !is AnimationImpl<T>) {
            error("Animation must be type of AnimationImpl")
        }

        animation.startAnimation()
        val task =
            Bukkit.getAsyncScheduler().runAtFixedRate(KIA.plugin, {
                animation.renderFrame(t)
            }, if (animation.startInstant) 0 else animation.interval, animation.interval, animation.timeUnit)
        runningAnimations[task] = animation
    }

    /**
     * Stops the given animation, if its running
     * @param animation to stp
     */
    fun <T> stopAnimation(animation: Animation<T>) {
        for ((key, value) in runningAnimations) {
            if (value == animation) {
                key.cancel()
            }
        }
    }
}
