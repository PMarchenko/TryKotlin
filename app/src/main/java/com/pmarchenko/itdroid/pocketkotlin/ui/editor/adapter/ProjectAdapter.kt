package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs.LogsContentData
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs.LogsViewHolder
import com.pmarchenko.itdroid.pocketkotlin.model.log.LogRecord
import com.pmarchenko.itdroid.pocketkotlin.model.project.ErrorSeverity
import com.pmarchenko.itdroid.pocketkotlin.model.project.Project
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectExecutionResult
import com.pmarchenko.itdroid.pocketkotlin.recycler.ContentData
import com.pmarchenko.itdroid.pocketkotlin.recycler.DiffAdapter

/**
 * @author Pavel Marchenko
 */
class ProjectAdapter(
    private val context: Context,
    private val callback: EditorCallback
) : DiffAdapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_LOGS = 0
        private const val VIEW_TYPE_PROJECT_FILE = 1
    }

    private var project: Project? = null
    private var executionResult: ProjectExecutionResult? = null
    private var logs = listOf<LogRecord>()

    private val inflater = LayoutInflater.from(context)
    private val content = ArrayList<ContentData>()

    private val pendingSelections = mutableMapOf<String, Pair<Int, Int>>()

    override fun getItemCount() = content.size

    override fun getItemViewType(position: Int): Int {
        return content[position].viewType
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> getItem(position: Int): T = (content[position]) as T

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.itemAnimator = ProjectItemsAnimator()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        VIEW_TYPE_LOGS -> LogsViewHolder(
            inflater.inflate(R.layout.content_logs, parent, false), callback
        )
        VIEW_TYPE_PROJECT_FILE -> ProjectFileViewHolder(
            inflater.inflate(R.layout.content_project_file, parent, false), callback
        )
        else -> error("Unsupported viewType=$viewType")
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LogsViewHolder -> holder.bindView((getItem<LogsContentData>(position)).logs)
            is ProjectFileViewHolder -> holder.bindView(getItem(position))
            else -> error("Unsupported viewType=${holder.itemViewType}")
        }
    }

    fun updateState(project: Project?, executionResults: ProjectExecutionResult?) {
        this.project = project
        this.executionResult = executionResults

        updateContent()
    }

    fun applySelection(fileName: String, line: Int, linePosition: Int) {
        pendingSelections[fileName] = Pair(line, linePosition)
        updateContent()
    }

    fun setLog(log: List<LogRecord>) {
        if (this.logs != log) {
            this.logs = log
            updateContent()
        }
    }

    fun getTitle(position: Int): CharSequence {
        return when (getItemViewType(position)) {
            VIEW_TYPE_LOGS -> {
                if (logs.isEmpty()) {
                    context.getString(R.string.editor_tab_log_no_logs)
                } else {
                    context.getString(R.string.editor_tab_log, logs.size)
                }
            }
            VIEW_TYPE_PROJECT_FILE -> {
                val fileData = getItem<FileContentData>(position)
                val fileName = fileData.file.name
                val errors = fileData.errors
                val hasErrors = errors.any { it.severity == ErrorSeverity.ERROR }
                val hasWarnings = errors.any { it.severity == ErrorSeverity.WARNING }
                if (hasErrors || hasWarnings) {
                    val out = SpannableStringBuilder(fileName)
                    out.append(" ")
                    val icon = if (hasErrors) R.drawable.ic_error_12dp else R.drawable.ic_warning_12dp
                    out.setSpan(
                        ImageSpan(context, icon, ImageSpan.ALIGN_BASELINE),
                        out.length - 1,
                        out.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                    out
                } else {
                    fileName
                }
            }
            else -> error("Unsupported title position=$position")
        }
    }

    private fun updateContent() {
        val oldContent = ArrayList(content)
        content.clear()
        content.addAll(createContent())
        dispatchUpdates(oldContent, content)
    }

    private fun createContent(): ArrayList<ContentData> {
        val out = ArrayList<ContentData>()
        project?.let { project ->
            out.add(LogsContentData(VIEW_TYPE_LOGS, logs))

            val files = project.files.values
            files.forEach { file ->
                var errors = executionResult?.errors?.get(file.name)
                errors = if (errors == null) ArrayList() else ArrayList(errors)
                val selection = pendingSelections.remove(file.name)
                out.add(FileContentData(VIEW_TYPE_PROJECT_FILE, file, errors, selection))
            }
        }
        return out
    }

    fun getFilePosition(fileName: String): Int {
        return content.indexOfFirst { data -> data is FileContentData && data.file.name == fileName }
    }
}