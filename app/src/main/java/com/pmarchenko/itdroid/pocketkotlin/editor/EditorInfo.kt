package com.pmarchenko.itdroid.pocketkotlin.editor

import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectFile

/**
 * @author Pavel Marchenko
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
