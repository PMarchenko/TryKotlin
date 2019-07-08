package com.pmarchenko.itdroid.pocketkotlin.extentions

import android.content.res.Resources
import android.util.Log
import android.view.View
import android.view.ViewPropertyAnimator
import androidx.annotation.FloatRange
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @author Pavel Marchenko
 */
fun Int.dp(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Float.dp(): Float = this * Resources.getSystem().displayMetrics.density

fun ViewPropertyAnimator.scale(@FloatRange(from = 0.0, to = 1.0) value: Float): ViewPropertyAnimator {
    scaleX(value)
    scaleY(value)
    return this
}

fun SimpleDateFormat.format(timestamp: Long): String = format(Date(timestamp))

fun <V : View> RecyclerView.ViewHolder.findView(@IdRes id: Int): ReadOnlyProperty<RecyclerView.ViewHolder, V> =
    object : ReadOnlyProperty<RecyclerView.ViewHolder, V> {
        override fun getValue(thisRef: RecyclerView.ViewHolder, property: KProperty<*>): V {
            return thisRef.itemView.findViewById(id)
                ?: throw IllegalArgumentException("Cannot find view with id 0x${id.toString(16)}")
        }
    }

inline fun <T> T.measure(tag: String = "MeasureUtils", block: T.() -> Unit): T {
    val start = System.nanoTime()
    block()
    val end = System.nanoTime()
    Log.e(tag, "block execution took ${(end - start) / 1e6}ms")

    return this
}
