package de.staticred.kia.animation

/**
 * Models if a model can be animated
 */
interface Animatable<T> {

    /**
     * All animations
     * Identifier maps to the animation
     */
    val animations: MutableMap<String, Animation<T>>

    /**
     * The current animation if one is running
     */
    var currentAnimation: Animation<T>?

    /**
     * Whether the object is currently inside an animation
     * @return whether the model is animating or not
     */
    fun isAnimating(): Boolean

    /**
     * Adds the animation
     * @param identifier unique identifier to map the animation to
     * @param animation the animation
     */
    fun addAnimation(identifier: String, animation: Animation<T>)

    /**
     * Starts the given animation, but will not save it
     * @param animation the animation
     */
    fun startAnimation(animation: Animation<T>)

    /**
     * Starts the animation with the given identifier
     * @param identifier the identifier of the animation
     */
    fun startAnimation(identifier: String)

}