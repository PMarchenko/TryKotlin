package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectExecutionResult
import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project

/**
 * @author Pavel Marchenko
 */
data class EditorViewState(
    val project: Project?,
    var progressVisibility: Boolean = false,
    var executionResult: ProjectExecutionResult? = null
)