package de.staticred.kia.animation

interface Animation<T> {

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