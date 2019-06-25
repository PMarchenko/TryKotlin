package com.pmarchenko.itdroid.trykotlin.editor

import com.pmarchenko.itdroid.trykotlin.model.CodeExecutionResult
import com.pmarchenko.itdroid.trykotlin.view.State

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
data class EditorViewState(
    var progressVisibility: Boolean = false,
    var infoMessage: String = "",
    var errorMessage: String = "",
    var codeExecutionResult: CodeExecutionResult? = null
) : State