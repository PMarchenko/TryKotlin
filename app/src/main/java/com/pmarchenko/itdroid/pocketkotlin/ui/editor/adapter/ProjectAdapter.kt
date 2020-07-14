package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ImageSpan
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ErrorSeverity
import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectError
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentAdapter
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentData
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.HolderDelegate

/**
 * @author Pavel Marchenko
 */
class ProjectAdapter(private val context: Context, callback: EditorCallback) : ContentAdapter() {

    private var recyclerView: RecyclerView? = null

    private var project: Project? = null
    private var errors = emptyMap<String, List<ProjectError>>()
    private val selections = mutableMapOf<String, Pair<Int, Int>>()

    override val delegates: Map<Int, HolderDelegate<out RecyclerView.ViewHolder, out ContentData>> =
        mapOf(
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
            VIEW_TYPE_PROJECT_FILE -> {
                val fileData = getItem(position) as FileContentData
                val fileName = fileData.file.name
                val errors = fileData.errors
                val hasErrors = errors.any { it.severity == ErrorSeverity.ERROR }
                val hasWarnings = errors.any { it.severity == ErrorSeverity.WARNING }
                if (hasErrors || hasWarnings) {
                    val out = SpannableStringBuilder("$fileName ")
                    val icon = if (hasErrors) R.drawable.ic_error_12dp
                    else R.drawable.ic_warning_12dp
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
            if (getItemViewType(position) == VIEW_TYPE_PROJECT_FILE &&
                (getItem(position) as FileContentData).file.name == fileName
            ) {
                return position
            }
        }

        return RecyclerView.NO_POSITION
    }

    fun setProject(project: Project?, errors: Map<String, List<ProjectError>>?) {
        this.project = project
        this.errors = errors ?: emptyMap()
        updateContent()
    }

    fun applySelection(fileName: String, line: Int, linePosition: Int) {
        selections[fileName] = Pair(line, linePosition)
        updateContent()
    }

    private fun updateContent() {
        val content = mutableListOf<ContentData>()
        project?.let { project ->
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

        private const val VIEW_TYPE_PROJECT_FILE = 0

    }
}