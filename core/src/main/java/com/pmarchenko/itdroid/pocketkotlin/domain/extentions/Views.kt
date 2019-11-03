package com.pmarchenko.itdroid.pocketkotlin.domain.extentions

import android.app.Activity
import android.view.View
import android.view.View.*
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @author Pavel Marchenko
 */
fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) VISIBLE else GONE
}

fun View.isVisible() = visibility == VISIBLE

fun View.isRtl() = layoutDirection == LAYOUT_DIRECTION_RTL

fun View.centerX() = (right - left) / 2

fun View.centerY() = (bottom - top) / 2

fun <V : View> RecyclerView.ViewHolder.bindView(@IdRes id: Int): ReadOnlyProperty<RecyclerView.ViewHolder, V> =
    object : ReadOnlyProperty<RecyclerView.ViewHolder, V> {
        override fun getValue(thisRef: RecyclerView.ViewHolder, property: KProperty<*>): V {
            return itemView.findViewById(id)
                ?: error("Cannot find view with id 0x${id.toString(16)}")
        }
    }

fun <V : View> Fragment.bindView(@IdRes id: Int): ReadOnlyProperty<Fragment, V> =
    object : ReadOnlyProperty<Fragment, V> {
        override fun getValue(thisRef: Fragment, property: KProperty<*>): V {
            return view?.findViewById(id) ?: error("Cannot find view with id 0x${id.toString(16)}")
        }
    }

fun <V : View> Activity.bindView(@IdRes id: Int): ReadOnlyProperty<Activity, V> =
    object : ReadOnlyProperty<Activity, V> {
        override fun getValue(thisRef: Activity, property: KProperty<*>): V {
            return findViewById(id) ?: error("Cannot find view with id 0x${id.toString(16)}")
        }
    }

fun <V : View> Fragment.findView(@IdRes id: Int): V = view?.findViewById(id)
    ?: error("Cannot find view with id 0x${id.toString(16)}")
