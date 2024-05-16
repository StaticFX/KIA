package de.staticred.kia.inventory.builder

import de.staticred.kia.animation.Animation
import de.staticred.kia.animation.AnimationImpl
import java.util.concurrent.TimeUnit

/**
 * Builds a new animation
 *
 * To start this animation use the AnimationManager
 * @see de.staticred.kia.animation.AnimationManager
 *
 * @param T the class the animation should be run on
 * @param frames the amount of frames of the animation. This will determine how many times the animation tick function is ran
 * @see Animation.onAnimationFrame
 *
 * @param interval the delay between 2 animation frames
 * @param unit the timeUnit for the interval
 * @param init the init function
 *
 * @return newly build animation
 */
fun <T> animation(frames: Int, interval: Long, unit: TimeUnit, init: AnimationImpl<T>.() -> Unit): Animation<T> {
    return AnimationImpl<T>(frames, interval, unit, false).apply(init)
}

/**
 * Builds a new animation, which runs endless unless stopped
 *
 * To start the animation use the AnimationManager
 * @see de.staticred.kia.animation.AnimationManager
 *
 * @param T the class to run the animation on
 *
 * @param interval the delay between 2 frames
 * @see Animation.onAnimationFrame
 *
 * @param unit the timeunit for the interval
 * @param init the init function
 *
 * @return new animation which when started runs endless
 */
fun <T> endlessAnimation(interval: Long, unit: TimeUnit, init: AnimationImpl<T>.() -> Unit): Animation<T> {
    return AnimationImpl<T>(0, interval, unit, true).apply(init)
}