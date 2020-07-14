package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.databinding.ViewholderProjectBinding
import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.ProjectCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.HolderDelegate
import kotlinx.android.extensions.LayoutContainer

/**
 * @author Pavel Marchenko
 */
class HolderDelegateProject(private val callback: ProjectCallback) :
    HolderDelegate<HolderDelegateProject.ProjectViewHolder, ProjectContentData>() {

    override fun create(inflater: LayoutInflater, parent: ViewGroup): ProjectViewHolder {
        return ProjectViewHolder(
            inflater.inflate(R.layout.viewholder_project, parent, false),
            callback
        )
    }

    override fun bind(holder: ProjectViewHolder, position: Int, data: ProjectContentData) {
        holder.bind(data.project)
    }

    class ProjectViewHolder(
        override val containerView: View,
        private val projectListCallback: ProjectCallback
    ) : RecyclerView.ViewHolder(containerView), View.OnClickListener, LayoutContainer {

        private val binding = ViewholderProjectBinding.bind(containerView)
        private var project: Project? = null

        init {
            binding.root.setOnClickListener(this)
            binding.menu.setOnClickListener(this)
        }

        fun bind(project: Project) {
            this.project = project
            binding.editor.setText(project.demoProgram())
            binding.editor.handleTouchEvents = false
            binding.name.text = project.name

            binding.date.text = DateUtils.formatDateTime(
                binding.date.context,
                if (project.dateModified > 0) project.dateModified else project.dateCreated,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_TIME
            )
        }

        override fun onClick(v: View?) {
            project?.let {
                when (v) {
                    itemView -> projectListCallback.onProjectClick(it)
                    binding.menu -> projectListCallback.onProjectMenuClick(binding.menu, it)
                }
            }
        }
    }
}

private fun Project.demoProgram() =
    files.firstOrNull { it.name == mainFile }
        ?.program ?: ""
