package com.pmarchenko.itdroid.pocketkotlin.editor

import android.content.Context
import android.text.Editable
import androidx.core.widget.doAfterTextChanged
import com.pmarchenko.itdroid.pocketkotlin.view.ProgramEditor

/**
 * @author Pavel Marchenko
 */
class EditorProvider(
    private val bgColor: Int,
    private val textColor: Int,
) : (Context) -> ProgramEditor {

    var editAction: ((Editable) -> Unit)? = null

    override fun invoke(context: Context): ProgramEditor {
        return ProgramEditor(context).apply {
            setBackgroundColor(bgColor)
            setTextColor(textColor)

            doAfterTextChanged {
                it?.let { editAction?.invoke(it) }
            }
        }
    }
}