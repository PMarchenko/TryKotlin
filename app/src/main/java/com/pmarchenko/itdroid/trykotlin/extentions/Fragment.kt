package com.pmarchenko.itdroid.trykotlin.extentions

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
fun <V : View> Fragment.findView(@IdRes id: Int): ReadOnlyProperty<Fragment, V> =
    object : ReadOnlyProperty<Fragment, V> {
        override fun getValue(thisRef: Fragment, property: KProperty<*>): V {
            return thisRef.view?.findViewById(id) ?: throw IllegalArgumentException("Cannot find view with id 0x${id.toString(16)}")
        }
    }