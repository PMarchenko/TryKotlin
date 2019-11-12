package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import androidx.lifecycle.*
import com.pmarchenko.itdroid.pocketkotlin.data.model.Error
import com.pmarchenko.itdroid.pocketkotlin.data.model.Loading
import com.pmarchenko.itdroid.pocketkotlin.data.model.Resource
import com.pmarchenko.itdroid.pocketkotlin.data.model.Success
import com.pmarchenko.itdroid.pocketkotlin.data.model.log.*
import com.pmarchenko.itdroid.pocketkotlin.data.model.project.ProjectExecutionResult
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.domain.executor.ThrottleExecutor
import com.pmarchenko.itdroid.pocketkotlin.domain.repository.ProjectsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * @author Pavel Marchenko
 */
class EditorViewModel(
    private val projectRepo: ProjectsRepository,
    executorFactory: (CoroutineScope) -> ThrottleExecutor
) : ViewModel() {

    private val saveExecutor: ThrottleExecutor = executorFactory(viewModelScope)

    private val _log = LogLiveData()
    val log: LiveData<List<LogRecord>> = _log

    private val projectId = MutableLiveData<Long>()
    private val project: LiveData<Project> =
        Transformations.switchMap(projectId) { projectRepo.loadProject(it) }

    private val _viewState = MediatorLiveData<EditorViewState>()
        .apply {
            addSource(project) { value = value?.copy(project = it) ?: EditorViewState(it) }
        }
    val viewState: LiveData<EditorViewState> = _viewState

    fun loadProject(id: Long) {
        if (project.value?.id != id) {
            projectId.value = id
        }
    }

    val hasProject = project.value != null

    fun getProject(): Project? = project.value

    fun getProjectArgs() = project.value?.args ?: ""

    fun executeProject() {
        val project = this.project.value ?: return
        viewModelScope.launch {
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
        viewState.value?.let { state ->
            state.executionResult?.errors?.remove(file.name)
        }
        saveExecutor.execute {
            projectRepo.updateFile(project, file.copy(program = program))
        }
    }

    fun updateProjectName(project: Project, name: String) {
        viewModelScope.launch {
            projectRepo.updateProject(project.copy(name = name))
        }
    }

    fun resetExample(project: Project) {
        viewModelScope.launch {
            projectRepo.resetExampleProject(project)
        }
    }

    private fun asState(resource: Resource<ProjectExecutionResult>): EditorViewState {
        return when (resource) {
            is Success -> EditorViewState(project.value, executionResult = resource.data)
            is Loading -> EditorViewState(project.value, progressVisibility = true)
            is Error -> EditorViewState(project.value, errorMessage = resource.message)
            else -> error("Unsupported resource $resource")
        }
    }

    private fun logResource(resource: Resource<ProjectExecutionResult>) {
        when (resource) {
            is Success -> {
                val result = resource.data

                _log.postValue(InfoLogRecord(result.text ?: ""))
                result.exception?.let { _log.postValue(ExceptionLogRecord(it)) }

                if (result.hasErrors()) {
                    _log.postValue(
                        ErrorLogRecord(
                            ErrorLogRecord.ERROR_PROJECT,
                            errors = result.errors
                        )
                    )
                }

                result.testResults?.let { _log.postValue(TestResultsLogRecord(it)) }
            }
            is Error -> {
                _log.postValue(
                    ErrorLogRecord(ErrorLogRecord.ERROR_MESSAGE, resource.message)
                )
            }
        }
    }
}