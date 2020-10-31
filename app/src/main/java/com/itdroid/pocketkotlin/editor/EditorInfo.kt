package com.itdroid.pocketkotlin.editor

import com.itdroid.pocketkotlin.projects.model.Project
import com.itdroid.pocketkotlin.projects.model.ProjectFile

/**
 * @author itdroid
 */
data class EditorInfo(
    val project: Project,
    val selectedFileId: Long = -1,
    val isExecuting: Boolean = false,
) {

    fun selectedFile(): ProjectFile =
        project.files
            .find { it.id == selectedFileId }
            ?: project.files.first()
}
