package com.pmarchenko.itdroid.pocketkotlin

import android.view.ViewPropertyAnimator
import android.widget.EditText
import androidx.annotation.FloatRange
import androidx.annotation.StringRes
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Pavel Marchenko
 */

/**
 * Hack to be sure that when statement handled all possible results.
 * If result of when expression is used compiler does this by its own.
 * If result of when expression is not used compiler does NOT check all cases.
 * For example
 *
 * enum class State { A, B, C}
 *
 * Compile time error:
 * val data: Any = when(someValue) {
 *      A -> "One"
 *      B -> "Two"
 * }
 * No error:
 * when(someValue) {
 *      A -> println("Hello")
 *      B -> println("Bye")
 * }
 * but compile time error:
 * when(someValue) {
 *      A -> println("Hello")
 *      B -> println("Bye")
 * }.checkAllMatched
 * */
val <T> T.checkAllMatched: T
    get() = this

fun SimpleDateFormat.formatTimestamp(timestamp: Long): String = format(Date(timestamp))

fun EditText.setTextAndSelection(@StringRes id: Int) {
    setText(id)
    setSelection(text.length)
}

fun EditText.setTextAndSelection(text: CharSequence) {
    setText(text)
    setSelection(text.length)
}

fun ViewPropertyAnimator.scale(@FloatRange(from = 0.0, to = 1.0) value: Float): ViewPropertyAnimator {
    scaleX(value)
    scaleY(value)
    return this
}