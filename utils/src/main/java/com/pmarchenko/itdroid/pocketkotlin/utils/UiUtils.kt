package com.pmarchenko.itdroid.pocketkotlin.utils

import android.content.res.Resources

/**
 * @author Pavel Marchenko
 */
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Float.dp: Float
    get() = this * Resources.getSystem().displayMetrics.density