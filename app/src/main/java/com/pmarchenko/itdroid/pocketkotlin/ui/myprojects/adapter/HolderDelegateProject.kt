package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.adapter

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.domain.extentions.bindView
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorView
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.ProjectCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.HolderDelegate

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

    override fun bind(holder: ProjectViewHolder, position: Int, contentData: ProjectContentData) {
        holder.bind(contentData.project)
    }

    class ProjectViewHolder(itemView: View, private val projectListCallback: ProjectCallback) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val editorView by bindView<EditorView>(R.id.editor)
        private val nameView by bindView<TextView>(R.id.name)
        private val dateView by bindView<TextView>(R.id.date)
        private val menuView by bindView<View>(R.id.menu)

        private var project: Project? = null

        init {
            itemView.setOnClickListener(this)
            menuView.setOnClickListener(this)
        }

        fun bind(project: Project) {
            this.project = project
            editorView.setText(project.mainFile().program)
            editorView.handleTouchEvents = false
            nameView.text = project.name

            val date = if (project.dateModified > 0) project.dateModified else project.dateCreated
            dateView.text = DateUtils.formatDateTime(
                dateView.context,
                date,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_TIME
            )
        }

        override fun onClick(v: View?) {
            project?.let {
                when (v) {
                    itemView -> projectListCallback.onProjectClick(it)
                    menuView -> projectListCallback.onProjectMenuClick(v, it)
                }
            }
        }
    }
}
