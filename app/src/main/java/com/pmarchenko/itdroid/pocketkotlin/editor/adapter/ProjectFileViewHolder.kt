package com.pmarchenko.itdroid.pocketkotlin.editor.adapter

import android.text.Editable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.editor.EditorView
import com.pmarchenko.itdroid.pocketkotlin.editor.ProjectFileEditCallback
import com.pmarchenko.itdroid.pocketkotlin.model.Project
import com.pmarchenko.itdroid.pocketkotlin.model.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.utils.TextWatcherAdapter

/**
 * @author Pavel Marchenko
 */
class ProjectFileViewHolder(itemView: View, val callback: ProjectFileEditCallback) : RecyclerView.ViewHolder(itemView) {

    private val codeEditor = itemView as EditorView

    private lateinit var project: Project
    private lateinit var file: ProjectFile
    private val textWatcher = object : TextWatcherAdapter() {
        override fun afterTextChanged(s: Editable?) {
            project.files[file.name]?.text = s.toString()
            callback.onProjectFileEdit(file)
        }
    }

    init {
        codeEditor.addTextChangedListener(textWatcher)
    }

    fun bindView(project: Project, file: ProjectFile) {
        this.project = project
        this.file = file
        codeEditor.removeTextChangedListener(textWatcher)
        codeEditor.setText(file.text)
        codeEditor.addTextChangedListener(textWatcher)
    }
}