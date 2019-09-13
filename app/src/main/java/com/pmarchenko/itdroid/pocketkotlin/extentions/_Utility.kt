package com.pmarchenko.itdroid.pocketkotlin.extentions

import android.app.Activity
import android.content.res.Resources
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.annotation.FloatRange
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

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

fun <V : View> findView(@IdRes id: Int): ReadOnlyProperty<RecyclerView.ViewHolder, V> =
    object : ReadOnlyProperty<RecyclerView.ViewHolder, V> {
        override fun getValue(thisRef: RecyclerView.ViewHolder, property: KProperty<*>): V {
            return thisRef.itemView.findViewById(id) ?: error("Cannot find view with id 0x${id.toString(16)}")
        }
    }

inline fun <K, V> Map<out K, V>.findAnyKey(opt: K, predicate: (V) -> Boolean): K {
    for (entry in this) {
        if (predicate(entry.value)) {
            return entry.key
        }
    }
    return opt
}