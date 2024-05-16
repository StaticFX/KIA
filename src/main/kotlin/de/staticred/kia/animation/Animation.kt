package de.staticred.kia.animation

/**
 *
 */
interface Animation<T> {

    /**
     * whether the animation should start instantly, or wait for the given delay until rendering the first frame
     */
    var startInstant: Boolean

    /**
     * Whether the animation should run endless or not
     */
    val endless: Boolean

    /**
     * Called when the next frame is rendered
     * @param listener function called whe the frame is rendered. Index is the current frame
     */
    fun onAnimationFrame(listener: T.(index: Int) -> Unit)

    /**
     * Stops the animation if it was running
     *
     * This will not trigger the onEnd listeners
     * @see onEnd
     */
    fun stop()

    /**
     * Called when the animation is finished
     */
    fun onEnd(listener: T.() -> Unit)

    /**
     * Whether the animation is running or not
     */
    fun isRunning(): Boolean
}