package com.pmarchenko.itdroid.pocketkotlin.extentions

import android.view.View
import android.view.View.*

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
