package com.pmarchenko.itdroid.trykotlin.editor

import androidx.lifecycle.Observer
import com.pmarchenko.itdroid.trykotlin.model.*
import com.pmarchenko.itdroid.trykotlin.presenter.Presenter

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
class EditorPresenter(view: EditorView, viewModel: EditorViewModel) : Presenter<EditorView, EditorViewModel, EditorViewState>(view, viewModel) {

    val project = SimpleProject()

    init {
        viewModel.executionResult.observe(view,
            Observer<Resource<ProjectExecutionResult>> { result ->
                if (result is ConsumableResource) {
                    if (!result.consumed) {
                        onCodeExecutionResult(result)
                        result.consumed = true
                    }
                } else {
                    onCodeExecutionResult(result)
                }
            }
        )
    }

    fun executeProject() {
        if (validProject(project)) {
            viewModel.executeProgram(project)
        } else {
            onCodeExecutionResult(Error("Empty project"))
        }
    }

    private fun onCodeExecutionResult(resource: Resource<ProjectExecutionResult>) {
        view.handleNewState(
            when (resource) {
                is Success -> {
                    EditorViewState(
                        codeExecutionResult = resource.data
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

    private fun validProject(project: Project): Boolean {
        return project.files.isNotEmpty()
    }
}