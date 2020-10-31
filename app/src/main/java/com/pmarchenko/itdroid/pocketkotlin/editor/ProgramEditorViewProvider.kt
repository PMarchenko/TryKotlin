package com.pmarchenko.itdroid.pocketkotlin.editor

import android.content.Context
import android.text.Editable
import androidx.core.widget.doAfterTextChanged

/**
 * @author Pavel Marchenko
 */
class ProgramEditorViewProvider(
    private val bgColor: Int,
    private val textColor: Int,
) : (Context) -> ProgramEditorView {

    var editAction: ((Editable) -> Unit)? = null

    override fun invoke(context: Context): ProgramEditorView {
        return ProgramEditorView(context).apply {
            setBackgroundColor(bgColor)
            setTextColor(textColor)

            doAfterTextChanged {
                it?.let { editAction?.invoke(it) }
            }
        }
    }
}