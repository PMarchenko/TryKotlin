package com.pmarchenko.itdroid.pocketkotlin.core.extentions

import android.content.res.Resources
import android.view.ViewPropertyAnimator
import androidx.annotation.FloatRange
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Pavel Marchenko
 */
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.dp: Float
    get() = this * Resources.getSystem().displayMetrics.density

fun ViewPropertyAnimator.scale(@FloatRange(from = 0.0, to = 1.0) value: Float): ViewPropertyAnimator {
    scaleX(value)
    scaleY(value)
    return this
}

fun SimpleDateFormat.formatTimestamp(timestamp: Long): String = format(Date(timestamp))

inline fun <K, V> Map<out K, V>.findKey(opt: K, predicate: (V) -> Boolean): K {
    for (entry in this) {
        if (predicate(entry.value)) return entry.key
    }
    return opt
}