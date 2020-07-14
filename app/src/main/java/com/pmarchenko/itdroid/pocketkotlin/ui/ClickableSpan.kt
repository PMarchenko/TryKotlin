package com.pmarchenko.itdroid.pocketkotlin.ui

import android.view.View


/**
 * @author Pavel Marchenko
 */
class ClickableSpan<T>(private val data: T, private val listener: ClickableSpanListener<T>) :
    android.text.style.ClickableSpan() {

    override fun onClick(widget: View) {
        listener.onClick(data, widget)
    }
}

interface ClickableSpanListener<T> {

    fun onClick(data: T, view: View)
}