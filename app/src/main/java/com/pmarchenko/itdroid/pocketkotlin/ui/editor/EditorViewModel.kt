package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import android.app.Application
import androidx.lifecycle.*
import com.pmarchenko.itdroid.pocketkotlin.checkAllMatched
import com.pmarchenko.itdroid.pocketkotlin.projects.ProjectsRepository
import com.pmarchenko.itdroid.pocketkotlin.projects.model.*
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs.*
import com.pmarchenko.itdroid.pocketkotlin.utils.ThrottleExecutor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * @author Pavel Marchenko
 */
class EditorViewModel(app: Application) : AndroidViewModel(app) {

    private val projectRepo = ProjectsRepository.newInstance(app)

    private val saveExecutor: ThrottleExecutor = ThrottleExecutor.forScope(viewModelScope)

    private val _log = LogLiveData()
    val log: LiveData<List<LogRecord>> = _log

    private val projectId = MutableLiveData<Long>()
    private val project: LiveData<Project?> =
        projectId.switchMap {
            _viewState.value = asState(Loading())
            projectRepo.loadProject(it)
        }

    private val _viewState = MediatorLiveData<EditorViewState>()
        .apply {
            addSource(project) {
                value = value?.copy(project = it, progressVisibility = false) ?: EditorViewState(it)
            }
        }
    val viewState: LiveData<EditorViewState> = _viewState

    fun loadProject(id: Long) {
        if (project.value?.id != id) {
            projectId.value = id
        }
    }

    val hasProject
        get() = project.value != null

    fun getProject(): Project? = project.value

    fun getProjectArgs() = project.value?.args ?: ""

    fun executeProject() {
        val project = this.project.value ?: return
        viewModelScope.launch {
            @Suppress("EXPERIMENTAL_API_USAGE")
            projectRepo.execute(project)
                .onStart {
                    _log.postValue(RunLogRecord(project.name, project.args))
                    emit(Loading())
                }
                .map {
                    logResource(it)
                    asState(it)
                }
                .collect { _viewState.value = it }
        }
    }

    fun clearLogs() {
        _log.clearLogs()
    }

    fun setCommandLineArgs(args: String) {
        project.value?.let { project ->
            viewModelScope.launch {
                projectRepo.updateProject(project.copy(args = args))
            }
        }
    }

    fun editProjectFile(project: Project, file: ProjectFile, program: String) {
        file.program = program

        _viewState.value = viewState.value?.copy(executionResult = null)

        saveExecutor.execute {
            projectRepo.updateFile(project, file.copy(program = program))
        }
    }

    fun updateProjectName(project: Project, name: String) {
        viewModelScope.launch { projectRepo.updateProject(project.copy(name = name)) }
    }

    private fun asState(resource: Resource<ProjectExecutionResult>): EditorViewState {
        return when (resource) {
            is Loading -> EditorViewState(project.value, progressVisibility = true)
            is Success -> EditorViewState(project.value, executionResult = resource.data)
            is Error -> EditorViewState(project.value, progressVisibility = false)
        }
    }

    private fun logResource(resource: Resource<ProjectExecutionResult>) {
        when (resource) {
            is Success -> {
                val result = resource.data

                _log.postValue(InfoLogRecord(result.text ?: ""))
                result.exception?.let { _log.postValue(ExceptionLogRecord(it)) }

                if (result.hasErrors) {
                    _log.postValue(
                        ErrorLogRecord(ErrorTypeType.ERROR_PROJECT, errors = result.errors)
                    )
                }

                result.testResults?.let { _log.postValue(TestResultsLogRecord(it)) }
            }
            is Error -> {
                _log.postValue(ErrorLogRecord(ErrorTypeType.ERROR_MESSAGE, resource.message))
            }
            is Loading -> {
            }
        }.checkAllMatched
    }
}

private val ProjectExecutionResult.hasErrors: Boolean
    get() = errors.any { it.value.isNotEmpty() }
