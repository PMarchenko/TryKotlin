package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectExecutionResult
import com.pmarchenko.itdroid.pocketkotlin.model.ViewState
import com.pmarchenko.itdroid.pocketkotlin.db.entity.Project

/**
 * @author Pavel Marchenko
 */
data class EditorViewState(
    val project: Project?,
    var progressVisibility: Boolean = false,
    var errorMessage: String? = null,
    var executionResult: ProjectExecutionResult? = null

) : ViewState {
    override fun consume() {
        errorMessage = null
    }
}