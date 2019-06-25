package com.pmarchenko.itdroid.trykotlin.editor

import androidx.lifecycle.Observer
import com.pmarchenko.itdroid.trykotlin.model.*
import com.pmarchenko.itdroid.trykotlin.presenter.Presenter

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
class EditorPresenter(view: EditorView, viewModel: EditorViewModel) : Presenter<EditorView, EditorViewModel, EditorViewState>(view, viewModel) {

    init {
        viewModel.executionResult.observe(view,
            Observer<Resource<CodeExecutionResult>> { result ->
                if (result is ConsumableResource) {
                    if (!result.consumed) {
                        result.consumed = true
                        onCodeExecutionResult(result)
                    }
                } else {
                    onCodeExecutionResult(result)
                }
            }
        )
    }

    fun executeProgram(program: String) {
        viewModel.executeProgram(program)
    }

    private fun onCodeExecutionResult(resource: Resource<CodeExecutionResult>) {
        view.handleNewState(
            when (resource) {
                is Success -> {
                    EditorViewState(
                        codeExecutionResult = resource.data,
                        infoMessage = "Success"
                    )
                }
                is Loading -> {
                    EditorViewState(progressVisibility = true)
                }
                is Error -> {
                    EditorViewState(errorMessage = resource.message)
                }
                else -> throw IllegalArgumentException("Unsupported resource $resource")
            }
        )
    }
}