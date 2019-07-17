package com.pmarchenko.itdroid.pocketkotlin.editor.adapter

import android.text.Editable
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.editor.EditorView
import com.pmarchenko.itdroid.pocketkotlin.editor.ProjectCallback
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectError
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.utils.TextWatcherAdapter

/**
 * @author Pavel Marchenko
 */
class ProjectFileViewHolder(itemView: View, private val callback: ProjectCallback) : RecyclerView.ViewHolder(itemView) {

    private val editor = itemView.findViewById<EditorView>(R.id.editor)

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
        if (this.fileData != fileData) {
            this.fileData = fileData
            bindProgram(fileData.file)
            bindErrors(fileData.file, fileData.errors)
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

    private fun bindErrors(file: ProjectFile, errors: List<ProjectError>) {
        editor.setErrors(file, errors)
    }

    private fun bindSelection(selection: Pair<Int, Int>?) {
        if (selection != null) {
            editor.setSelection(selection)
            editor.requestFocus()
        }
    }
}