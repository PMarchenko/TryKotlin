package com.pmarchenko.itdroid.pocketkotlin.editor

import com.pmarchenko.itdroid.pocketkotlin.model.ProjectExecutionResult
import com.pmarchenko.itdroid.pocketkotlin.model.ViewState

/**
 * @author Pavel Marchenko
 */
data class ProjectViewState(
    var progressVisibility: Boolean = false,
    var errorMessage: String? = null,
    var executionResult: ProjectExecutionResult? = null
) : ViewState {
    override fun consume() {
        errorMessage = null
    }
}