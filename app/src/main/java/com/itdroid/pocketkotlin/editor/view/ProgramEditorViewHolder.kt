package com.itdroid.pocketkotlin.editor.view

import android.content.Context
import android.view.ViewGroup
import android.widget.ScrollView

/**
 * @author itdroid
 */
class ProgramEditorViewHolder(context: Context) : ScrollView(context) {

    var selectionListener: ((IntRange) -> Unit)? = null

    val editor: EditorView

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        addView(EditorView(context).also { editor = it })
        editor.setSelectionListener { start, end ->
            selectionListener?.invoke(start..end)
        }
    }

    var text: CharSequence?
        get() = editor.text
        set(value) {
            editor.setText(value)
        }

    fun reset() {
        selectionListener = null
    }

    fun setSelection(start: Int, end: Int) {
        editor.setSelection(start, end)
    }
}
