package com.pmarchenko.itdroid.pocketkotlin.editor.adapter

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.editor.ProjectFileEditCallback
import com.pmarchenko.itdroid.pocketkotlin.model.Project
import com.pmarchenko.itdroid.pocketkotlin.model.ProjectExecutionResult
import com.pmarchenko.itdroid.pocketkotlin.model.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.model.log.LogRecord

/**
 * @author Pavel Marchenko
 */
class ProjectAdapter(
        private val context: Context,
        private val callback: ProjectFileEditCallback
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_LOGS = 0
        private const val VIEW_TYPE_PROJECT_FILE = 1
    }

    private var project: Project? = null
    var projectExecutionResult: ProjectExecutionResult? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var logs = listOf<LogRecord>()

    private val inflater = LayoutInflater.from(context)
    private val data = mutableListOf<Pair<Int, Any>>()

    init {
        setHasStableIds(true)
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int): Int {
        return data[position].first
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getItem(position: Int): T = (data[position].second) as T

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        VIEW_TYPE_LOGS -> {
            val view = inflater.inflate(R.layout.content_logs, parent, false)
            LogsViewHolder(view)
        }
        VIEW_TYPE_PROJECT_FILE -> {
            val view = inflater.inflate(R.layout.content_project_file, parent, false)
            ProjectFileViewHolder(view, callback)
        }
        else -> throw IllegalArgumentException("Unsupported viewType=$viewType")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LogsViewHolder -> holder.bindView(logs)
            is ProjectFileViewHolder -> holder.bindView(project!!, getItem(position))
            else -> throw IllegalArgumentException("Unsupported viewType=${holder.itemViewType}")
        }
    }

    fun setProject(project: Project) {
        if (this.project != project) {
            this.project = project
            data.clear()
            data.add(Pair(VIEW_TYPE_LOGS, logs))
            project.files.values.forEach { file ->
                data.add(VIEW_TYPE_PROJECT_FILE to file)
            }
            notifyUpdates()
        }
    }

    fun clearErrors(file: ProjectFile) {
        val errors = projectExecutionResult?.errors?.get(file.name)
        if (!errors.isNullOrEmpty()) {
            errors.clear()
            notifyUpdates()
        }
    }

    fun setLog(log: List<LogRecord>) {
        if (this.logs != log) {
            this.logs = log
            notifyUpdates()
        }
    }

    fun getTitle(position: Int): CharSequence {
        val item = data[position]
        return when (item.first) {
            VIEW_TYPE_LOGS -> {
                if (logs.isEmpty()) {
                    context.getString(R.string.editor_tab_log_no_logs)
                } else {
                    context.getString(R.string.editor_tab_log, logs.size)
                }
            }
            VIEW_TYPE_PROJECT_FILE -> {
                val fileName = getItem<ProjectFile>(position).name

                if (projectExecutionResult?.hasErrors(fileName) == true) {
                    val out = SpannableStringBuilder(fileName)
                    out.append(" ")
                    out.setSpan(ImageSpan(context, R.drawable.ic_warning_12dp, ImageSpan.ALIGN_BASELINE),
                            out.length - 1,
                            out.length,
                            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    out
                } else {
                    fileName
                }
            }
            else -> throw IllegalArgumentException("Unsupported title position=$position")
        }
    }

    private fun notifyUpdates() {
        //todo implement me
        notifyDataSetChanged()
    }
}