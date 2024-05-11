package de.staticred.kia.inventory.builder

import de.staticred.kia.animation.Animation
import de.staticred.kia.animation.AnimationImpl
import java.util.concurrent.TimeUnit

fun <T> animation(frames: Int, interval: Long, unit: TimeUnit, init: AnimationImpl<T>.() -> Unit): Animation<T> {
    return AnimationImpl<T>(frames, interval, unit, false).apply(init)
}

fun <T> endlessAnimation(interval: Long, unit: TimeUnit, init: AnimationImpl<T>.() -> Unit): Animation<T> {
    return AnimationImpl<T>(0, interval, unit, true).apply(init)
}