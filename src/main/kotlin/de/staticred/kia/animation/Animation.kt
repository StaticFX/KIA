package de.staticred.kia.animation

interface Animation<T> {

    /**
     * whether the animation should start instantly, or wait for the given delay until rendering the first frame
     */
    var startInstant: Boolean

    /**
     * Called when the next frame is rendered
     * @param listener function called whe the frame is rendered. Index is the current frame
     */
    fun onAnimationFrame(listener: T.(index: Int) -> Unit)

    /**
     * Called when the animation is finished
     */
    fun onEnd(listener: T.() -> Unit)

    /**
     * Whether the animation is running or not
     */
    fun isRunning(): Boolean
}