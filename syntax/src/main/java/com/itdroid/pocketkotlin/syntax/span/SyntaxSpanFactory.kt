package com.itdroid.pocketkotlin.syntax.span

import android.text.style.ForegroundColorSpan

/**
 * @author itdroid
 */
internal class SyntaxSpanFactory(private val color: Int) {

    fun create(): Any = ForegroundColorSpan(color)
}
