package com.pmarchenko.itdroid.pocketkotlin.editor

import androidx.annotation.UiThread
import androidx.lifecycle.*
import com.pmarchenko.itdroid.pocketkotlin.model.*
import com.pmarchenko.itdroid.pocketkotlin.model.log.LogLiveData
import com.pmarchenko.itdroid.pocketkotlin.model.log.LogRecord
import com.pmarchenko.itdroid.pocketkotlin.repository.KotlinProjectRepository

/**
 * @author Pavel Marchenko
 */
class ProjectViewModel : ViewModel() {

    val project = HelloWorldProject() as Project

    private val _log = LogLiveData()
    val log: LiveData<List<LogRecord>>
        get() = _log

    private val kProjectRepository = KotlinProjectRepository()

    private val projectExecutor = MutableLiveData<Project>()

    private val _viewState = MediatorLiveData<ProjectViewState>()

    val viewState: LiveData<ProjectViewState>
        get() = _viewState

    init {
        _viewState.addSource(
            Transformations.switchMap(projectExecutor) { project ->
                when (project) {
                    is KotlinProject -> kProjectRepository.execute(_log, project)
                    else -> error("Unsupported project")
                }
            }) { resource -> _viewState.value = asState(resource) }
    }

    fun executeProject() {
        projectExecutor.value = project
    }

    @UiThread
    fun clearLogs() {
        _log.value = arrayListOf()
    }

    @UiThread
    fun editProjectFile(file: ProjectFile, text: String) {
        project.files[file.name]?.text = text

        var state = viewState.value

        if (state != null) {
            val executionResult = state.executionResult
            if (executionResult != null) {
                val newResults = executionResult.copy()
                newResults.errors[file.name]?.clear()
                state = ProjectViewState(executionResult = newResults)
            }
        } else {
            state = ProjectViewState()
        }

        _viewState.value = state
    }

    private fun asState(resource: Resource<ProjectExecutionResult>) =
        when (resource) {
            is Success -> ProjectViewState(executionResult = resource.data)
            is Loading -> ProjectViewState(progressVisibility = true)
            is Error -> ProjectViewState(errorMessage = resource.message)
            else -> error("Unsupported resource $resource")
        }
}