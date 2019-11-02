package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.model.log.LogRecord
import com.pmarchenko.itdroid.pocketkotlin.model.project.ErrorSeverity
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectError
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs.LogsContentData
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentAdapter
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentData
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.HolderDelegate

/**
 * @author Pavel Marchenko
 */
class ProjectAdapter(private val context: Context, private val callback: EditorCallback) : ContentAdapter() {

    private var recyclerView: RecyclerView? = null

    private var project: Project? = null
    private var errors = emptyMap<String, ArrayList<ProjectError>>()
    private var logs = listOf<LogRecord>()
    private val selections = mutableMapOf<String, Pair<Int, Int>>()

    override fun delegates(): Map<Int, HolderDelegate<*, *>> =
        mapOf(
            VIEW_TYPE_LOGS to HolderDelegateLogs(callback),
            VIEW_TYPE_PROJECT_FILE to HolderDelegateProjectFile(callback)
        )

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
        recyclerView.itemAnimator = ProjectItemsAnimator()
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        this.recyclerView = null
    }

    fun getTitle(position: Int): CharSequence {
        return when (getItemViewType(position)) {
            VIEW_TYPE_LOGS -> {
                if (logs.isEmpty()) {
                    context.getString(R.string.editor__tab__log_no_logs)
                } else {
                    context.getString(R.string.editor__tab__log, logs.size)
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

    fun getFilePosition(fileName: String): Int {
        for (position in 0 until itemCount) {
            if (getItemViewType(position) == VIEW_TYPE_PROJECT_FILE && getItem<FileContentData>(position).file.name == fileName) {
                return position
            }
        }

        return RecyclerView.NO_POSITION
    }

    fun resetProject() {
        recyclerView?.recycledViewPool?.clear()
        setProject(null, null)
    }

    fun setProject(project: Project?, errors: Map<String, ArrayList<ProjectError>>?) {
        this.project = project
        this.errors = errors ?: emptyMap()
        updateContent()
    }

    fun applySelection(fileName: String, line: Int, linePosition: Int) {
        selections[fileName] = Pair(line, linePosition)
        updateContent()
    }

    fun setLog(log: List<LogRecord>) {
        if (this.logs != log) {
            this.logs = log
            updateContent()
        }
    }

    private fun updateContent() {
        val content = mutableListOf<ContentData>()
        project?.let { project ->
            content.add(LogsContentData(VIEW_TYPE_LOGS, logs))

            project.files.mapTo(content) { file ->
                FileContentData(
                    VIEW_TYPE_PROJECT_FILE,
                    project,
                    file,
                    errors[file.name]?.let { ArrayList(it) } ?: emptyList(),
                    selections.remove(file.name)
                )
            }
        }

        setContent(content)
    }

    companion object {
        private const val VIEW_TYPE_LOGS = 0
        private const val VIEW_TYPE_PROJECT_FILE = 1
    }
}