package de.staticred.kia.animation

import java.util.concurrent.TimeUnit


class AnimationImpl<T>(val frames: Int, val interval: Long, val timeUnit: TimeUnit): Animation<T> {

    private val onFrameListeners = mutableListOf<T.(index: Int) -> Unit>()
    private val onEndListeners = mutableListOf<T.() -> Unit>()

    private var currentFrame = 0
    private var running = false

    override fun onAnimationFrame(listener: T.(index: Int) -> Unit) {
        onFrameListeners += listener
    }

    override fun onEnd(listener: T.() -> Unit) {
        onEndListeners += listener
    }

    fun renderFrame(t: T) {
        onFrameListeners.forEach { it(t, currentFrame) }
        currentFrame++
        if (currentFrame >= frames) {
            AnimationManager.stopAnimation(this)
            running = false
            endAnimation(t)
        }
    }

    fun startAnimation() {
        running = true
    }

    override fun isRunning() = running

    private fun endAnimation(t: T) {
        onEndListeners.forEach { it(t) }
    }
}