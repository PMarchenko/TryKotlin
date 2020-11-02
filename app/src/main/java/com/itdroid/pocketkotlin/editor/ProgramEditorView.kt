package com.itdroid.pocketkotlin.editor

import android.content.Context
import android.text.InputFilter
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import com.itdroid.pocketkotlin.projects.FileMaxLength

/**
 * @author itdroid
 */
class ProgramEditorView(context: Context) : AppCompatEditText(context) {

    var selectionListener: ((IntRange) -> Unit)? = null

    init {
        layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        filters = arrayOf(InputFilter.LengthFilter(FileMaxLength))

        gravity = Gravity.START
    }

    fun reset() {
        selectionListener = null
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {
        super.onSelectionChanged(selStart, selEnd)

        selectionListener?.invoke(selStart..selEnd)
    }
}
