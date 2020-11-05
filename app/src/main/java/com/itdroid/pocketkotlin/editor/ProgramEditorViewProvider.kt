package com.itdroid.pocketkotlin.editor

import android.content.Context
import android.text.Editable
import androidx.core.widget.doAfterTextChanged
import com.itdroid.pocketkotlin.editor.view.ProgramEditorViewHolder

/**
 * @author itdroid
 */
class ProgramEditorViewProvider(private val textColor: Int) :
        (Context) -> ProgramEditorViewHolder {

    var editAction: ((Editable) -> Unit)? = null

    override fun invoke(context: Context): ProgramEditorViewHolder {
        return ProgramEditorViewHolder(context)
            .apply {
                editor.setTextColor(textColor)
                editor.doAfterTextChanged {
                    if (it != null) {
                        editAction?.invoke(it)
                    }
                }
            }
    }
}
