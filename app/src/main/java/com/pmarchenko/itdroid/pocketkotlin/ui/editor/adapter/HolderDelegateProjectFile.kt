package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.databinding.ViewholderProjectFileBinding
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.HolderDelegate
import kotlinx.android.extensions.LayoutContainer

/**
 * @author Pavel Marchenko
 */
class HolderDelegateProjectFile(private val callback: EditorCallback) :
    HolderDelegate<HolderDelegateProjectFile.ProjectFileViewHolder, FileContentData>() {

    override fun create(inflater: LayoutInflater, parent: ViewGroup): ProjectFileViewHolder {
        return ProjectFileViewHolder(
            inflater.inflate(R.layout.viewholder_project_file, parent, false),
            callback
        )
    }

    override fun bind(holder: ProjectFileViewHolder, position: Int, data: FileContentData) {
        holder.bind(data)
    }

    class ProjectFileViewHolder(
        override val containerView: View,
        private val callback: EditorCallback
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        private val binding = ViewholderProjectFileBinding.bind(containerView)
        private var fileData: FileContentData? = null

        init {
            binding.editor.doAfterTextChanged {
                val file = fileData?.file ?: return@doAfterTextChanged
                val program = it.toString()

                if (file.program != program) {
                    val project = fileData?.project ?: return@doAfterTextChanged
                    callback.onFileEdited(project, file, program)
                }
            }
            binding.editor.setProjectCallback(callback)
        }

        fun bind(fileData: FileContentData) {
            this.fileData = fileData

            val file = fileData.file
            val program = binding.editor.program

            if (program.isEmpty() && file.program != program) {
                binding.editor.setText(file.program)
            }

            binding.editor.setErrors(file, fileData.errors)

            fileData.selection?.let {
                binding.editor.setSelection(it)
                binding.editor.requestFocus()
                fileData.selection = null
            }
        }
    }
}