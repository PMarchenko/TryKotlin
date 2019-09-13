package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import androidx.annotation.UiThread
import androidx.lifecycle.*
import com.pmarchenko.itdroid.pocketkotlin.model.Error
import com.pmarchenko.itdroid.pocketkotlin.model.Loading
import com.pmarchenko.itdroid.pocketkotlin.model.Resource
import com.pmarchenko.itdroid.pocketkotlin.model.Success
import com.pmarchenko.itdroid.pocketkotlin.model.log.LogLiveData
import com.pmarchenko.itdroid.pocketkotlin.model.log.LogRecord
import com.pmarchenko.itdroid.pocketkotlin.model.project.*
import com.pmarchenko.itdroid.pocketkotlin.repository.KotlinProjectRepository

/**
 * @author Pavel Marchenko
 */
class EditorViewModel : ViewModel() {

    var project: Project? = EmptyProject()
        set(value) {
            field = value
            _viewState.value = EditorViewState(value)
        }

    private val _log = LogLiveData()

    val log: LiveData<List<LogRecord>> = _log

    private val kProjectRepository = KotlinProjectRepository()

    private val projectExecutor = MutableLiveData<Project>()

    private val _viewState = MediatorLiveData<EditorViewState>()

    val viewState: LiveData<EditorViewState> = _viewState

    init {
        _viewState.addSource(
            Transformations.switchMap(projectExecutor) { project ->
                when (project) {
                    is KotlinProject -> kProjectRepository.execute(_log, project)
                    else -> error("Unsupported project")
                }
            }) { resource -> _viewState.value = asState(resource) }

        _viewState.value = EditorViewState(project)
    }

    fun executeProject() {
        projectExecutor.value = project
    }

    @UiThread
    fun clearLogs() {
        _log.value = arrayListOf()
    }

    @UiThread
    fun setCommandLineArgs(args: String) {
        val localProjectCopy = project ?: error("Cannot edit args without a project")

        localProjectCopy.args = args

        var state = viewState.value
        if (state == null) {
            state = EditorViewState(project)
        }
        _viewState.value = state
    }

    @UiThread
    fun editProjectFile(file: ProjectFile, text: String) {
        val localProjectCopy = project ?: error("No project to edit")

        localProjectCopy.files[file.name]?.text = text

        var state = viewState.value

        if (state == null) {
            state = EditorViewState(project)
        } else {
            state = state.copy()
            state.executionResult?.let { executionResult ->
                executionResult.errors[file.name]?.clear()
            }
        }

        _viewState.value = state
    }

    private fun asState(resource: Resource<ProjectExecutionResult>) =
        when (resource) {
            is Success -> EditorViewState(project, executionResult = resource.data)
            is Loading -> EditorViewState(project, progressVisibility = true)
            is Error -> EditorViewState(project, errorMessage = resource.message)
            else -> error("Unsupported resource $resource")
        }
}