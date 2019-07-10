package com.pmarchenko.itdroid.pocketkotlin.editor.adapter

import android.text.Editable
import android.text.TextUtils
import android.view.View
import androidx.core.util.ObjectsCompat
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.editor.EditorView
import com.pmarchenko.itdroid.pocketkotlin.editor.ProjectCallback
import com.pmarchenko.itdroid.pocketkotlin.model.ProjectError
import com.pmarchenko.itdroid.pocketkotlin.model.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.utils.TextWatcherAdapter

/**
 * @author Pavel Marchenko
 */
class ProjectFileViewHolder(itemView: View, private val callback: ProjectCallback) : RecyclerView.ViewHolder(itemView) {

    private val editor = itemView as EditorView

    private var fileData: FileContentData? = null

    private val textWatcher = object : TextWatcherAdapter() {
        override fun afterTextChanged(s: Editable?) {
            val file = fileData?.file ?: return
            val text = s.toString()
            if (!TextUtils.equals(file.text, text)) callback.editProjectFile(file, text)
        }
    }

    init {
        editor.addTextChangedListener(textWatcher)
        editor.setProjectCallback(callback)
    }

    fun bindView(fileData: FileContentData) {
        if (!ObjectsCompat.equals(this.fileData, fileData)) {
            this.fileData = fileData
            bindProgram(fileData.file)
            bindErrors(fileData.file.name, fileData.errors)
            bindSelection(fileData.selection)
            fileData.selection = null
        }
    }

    private fun bindProgram(file: ProjectFile) {
        if (!TextUtils.equals(file.text, editor.text.toString())) {
            editor.selectionStart
            editor.setText(file.text)
        }
    }

    private fun bindErrors(fileName: String, errors: List<ProjectError>) {
        editor.setErrors(fileName, errors)
    }

    private fun bindSelection(selection: Pair<Int, Int>?) {
        val layout = editor.layout
        if (layout != null && selection != null) {
            val position = layout.getLineStart(selection.first) + selection.second
            editor.setSelection(position)
            editor.requestFocus()
        }
    }
}