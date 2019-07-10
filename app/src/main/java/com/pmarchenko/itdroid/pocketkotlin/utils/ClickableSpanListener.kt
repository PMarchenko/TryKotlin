package com.pmarchenko.itdroid.pocketkotlin.utils

import android.view.View

/**
 * @author Pavel Marchenko
 */
interface ClickableSpanListener<T> {

    fun onClick(data: T, view: View)
}