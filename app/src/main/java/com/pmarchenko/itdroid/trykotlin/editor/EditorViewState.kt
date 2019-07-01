package com.pmarchenko.itdroid.trykotlin.editor

import com.pmarchenko.itdroid.trykotlin.model.ProjectExecutionResult
import com.pmarchenko.itdroid.trykotlin.view.State

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
data class EditorViewState(
    var progressVisibility: Boolean = false,
    var errorMessage: String = "",
    var codeExecutionResult: ProjectExecutionResult? = null
) : State