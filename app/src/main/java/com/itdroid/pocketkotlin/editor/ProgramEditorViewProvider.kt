package com.itdroid.pocketkotlin.editor

import android.content.Context
import android.text.Editable
import androidx.core.widget.doOnTextChanged

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
            doOnTextChanged { text: CharSequence?, start: Int, before: Int, count: Int ->
                val callback = editAction
                if (callback != null && text is Editable) {
                    layout
                        ?.let { layout ->
                            val lineStart: Int = layout.getLineStart(layout.getLineForOffset(start))
                            val lineEnd: Int =
                                layout.getLineEnd(layout.getLineForOffset(start + count))
                            callback(text, lineStart..lineEnd)
                        }
                        ?: run {
                            callback(text, 0..text.length)
                        }
                }
            }
        }
    }
}
