package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter

import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorView
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.HolderDelegate
import com.pmarchenko.itdroid.pocketkotlin.utils.TextWatcherAdapter
import com.pmarchenko.itdroid.pocketkotlin.utils.logd

/**
 * @author Pavel Marchenko
 */
class HolderDelegateProjectFile(
    viewType: Int,
    private val callback: EditorCallback
) : HolderDelegate<HolderDelegateProjectFile.ProjectFileViewHolder, FileContentData>(viewType) {

    override fun create(inflater: LayoutInflater, parent: ViewGroup): ProjectFileViewHolder {
        return ProjectFileViewHolder(inflater.inflate(R.layout.viewholder_project_file, parent, false), callback)
    }

    override fun bind(holder: ProjectFileViewHolder, position: Int, contentData: FileContentData) {
        holder.bind(contentData)
    }

    class ProjectFileViewHolder(itemView: View, private val callback: EditorCallback) : RecyclerView.ViewHolder(itemView) {

        private val editor = itemView.findViewById<EditorView>(R.id.editor)

        private var fileData: FileContentData? = null

        private var pendingProgram: String = ""

        private val textWatcher = object : TextWatcherAdapter() {

            override fun afterTextChanged(s: Editable?) {
                val file = fileData?.file ?: return
                val project = fileData?.project ?: return
                val program = s.toString()

                if (file.program != program) {
                    pendingProgram = program
                    callback.onFileEdited(project, file, program)
                }
            }
        }

        init {
            editor.addTextChangedListener(textWatcher)
            editor.setProjectCallback(callback)
        }

        fun bind(fileData: FileContentData) {
            val file = fileData.file

            if (pendingProgram.isNotEmpty() && pendingProgram != file.program) {
                logd { "bindFile: reject" }
                return
            }

            if (this.fileData != fileData) {
                this.fileData = fileData

                if (file.program != editor.getProgram()) {
                    val selection = editor.selectionStart
                    logd { "bindFile: selection=$selection" }
                    editor.setText(file.program)
                    if (selection >= 0 && selection < editor.text?.length ?: -1) {
                        editor.setSelection(selection)
                        logd { "bindFile: apply selection=$selection" }
                    }
                }

                editor.setErrors(file, fileData.errors)

                fileData.selection?.let {
                    editor.setSelection(it)
                    editor.requestFocus()
                    fileData.selection = null
                }
            }
        }
    }
}