package com.itdroid.pocketkotlin.editor

import android.content.Context
import android.text.Editable
import androidx.core.widget.doAfterTextChanged

/**
 * @author itdroid
 */
class ProgramEditorViewProvider(
    private val bgColor: Int,
    private val textColor: Int,
) : (Context) -> ProgramEditorView {

    var editAction: ((Editable, IntRange) -> Unit)? = null

    override fun invoke(context: Context): ProgramEditorView {
        return ProgramEditorView(context).apply {
            setBackgroundColor(bgColor)
            setTextColor(textColor)
            doAfterTextChanged {
                if (it != null) {
                    editAction?.invoke(it, 0..it.length)
                }
            }
        }
    }
}
