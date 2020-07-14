package com.pmarchenko.itdroid.pocketkotlin.ui.experimental

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

/**
 * @author Pavel Marchenko
 */
fun main(context: Context) {
    val root = ui(context) {
        linearLayout {

            orientation = LinearLayout.VERTICAL
            weightSum = 100f

            textView {
                setBackgroundColor(Color.RED)
                layoutParams =
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
                        .apply {
                            weight = 33f
                        }
            }

            textView {
                setBackgroundColor(Color.GREEN)
                layoutParams =
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
                        .apply {
                            weight = 33f
                        }
            }

            textView {
                setBackgroundColor(Color.BLUE)
                layoutParams =
                    LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0)
                        .apply {
                            weight = 33f
                        }
            }
        }
    }
}

fun Fragment.ui(builder: UiBuilder.() -> View): View {
    return UiBuilder(requireContext()).builder()
}

fun ui(context: Context, builder: UiBuilder.() -> View): View {
    return UiBuilder(context).builder()
}

class UiBuilder(internal val context: Context)

fun UiBuilder.frameLayout(init: FrameLayout.() -> Unit): FrameLayout {
    val layout = FrameLayout(context)
    layout.init()
    return layout
}

fun ViewGroup.frameLayout(init: FrameLayout.() -> Unit) {
    val layout = FrameLayout(context)
    layout.init()
    addView(layout)
}

fun UiBuilder.linearLayout(init: LinearLayout.() -> Unit): LinearLayout {
    val layout = LinearLayout(context)
    layout.init()
    return layout
}

fun ViewGroup.linearLayout(init: LinearLayout.() -> Unit) {
    val layout = LinearLayout(context)
    layout.init()
    addView(layout)
}

fun ViewGroup.textView(init: TextView.() -> Unit): TextView {
    val textView = TextView(context)
    textView.init()
    addView(textView)
    return textView
}

fun ViewGroup.button(init: Button.() -> Unit) {
    val button = Button(context)
    button.init()
    addView(button)
}