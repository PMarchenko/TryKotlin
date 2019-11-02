package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import androidx.lifecycle.*
import com.pmarchenko.itdroid.pocketkotlin.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.db.entity.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.model.Error
import com.pmarchenko.itdroid.pocketkotlin.model.Loading
import com.pmarchenko.itdroid.pocketkotlin.model.Resource
import com.pmarchenko.itdroid.pocketkotlin.model.Success
import com.pmarchenko.itdroid.pocketkotlin.model.log.*
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectExecutionResult
import com.pmarchenko.itdroid.pocketkotlin.repository.ProjectsRepository
import com.pmarchenko.itdroid.pocketkotlin.utils.doInBackground
import com.pmarchenko.itdroid.pocketkotlin.utils.executor.TaskExecutor
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

/**
 * @author Pavel Marchenko
 */
class EditorViewModel(
    private val projectRepo: ProjectsRepository,
    private val saveExecutor: TaskExecutor
) : ViewModel() {

    private val projectExecutor = MutableLiveData<Project>()

    private val _log = LogLiveData()
    val log: LiveData<List<LogRecord>> = _log

    private val _projectId = MutableLiveData<Long>()
    private val _project: LiveData<Project> = Transformations.switchMap(_projectId) { projectRepo.loadProject(it) }

    private val _viewState = MediatorLiveData<EditorViewState>().apply {
        addSource(
            Transformations.switchMap(projectExecutor) { project ->
                projectRepo.execute(project)
                    .onStart {
                        emit(Loading())
                        _log.postValue(RunLogRecord(project.name, project.args))
                    }
                    .map {
                        logResource(it)
                        asState(it)
                    }
                    .onCompletion {}
                    .asLiveData()

            }
        ) { value = it }

        addSource(_project) { project ->
            value = value?.copy(project = project) ?: EditorViewState(project)
        }
    }
    val viewState: LiveData<EditorViewState> = _viewState

    fun loadProject(id: Long) {
        if (_project.value?.id != id) {
            _projectId.value = id
        }
    }

    fun hasProject() = _project.value != null

    fun getProject(): Project? = _project.value

    fun getProjectArgs() = _project.value?.args ?: ""

    fun executeProject() {
        _project.value?.let {
            projectExecutor.value = it
        }
    }

    fun clearLogs() {
        _log.value = arrayListOf()
    }

    fun setCommandLineArgs(args: String) {
        _project.value?.let { project ->
            doInBackground {
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
        doInBackground {
            projectRepo.updateProject(project.copy(name = name))
        }
    }

    fun resetExample(project: Project) {
        doInBackground {
            projectRepo.resetExampleProject(project)
        }
    }

    private fun asState(resource: Resource<ProjectExecutionResult>): EditorViewState {
        return when (resource) {
            is Success -> EditorViewState(_project.value, executionResult = resource.data)
            is Loading -> EditorViewState(_project.value, progressVisibility = true)
            is Error -> EditorViewState(_project.value, errorMessage = resource.message)
            else -> error("Unsupported resource $resource")
        }
    }

    private fun logResource(resource: Resource<ProjectExecutionResult>) {
        when (resource) {
            is Success -> {
                val result = resource.data
                val hasOutput = !result.text.isNullOrEmpty() || !(result.exception != null || result.hasErrors())

                if (hasOutput) {
                    _log.postValue(InfoLogRecord(result.text ?: ""))
                }

                result.exception?.let {
                    _log.postValue(ExceptionLogRecord(it))
                }

                if (result.hasErrors()) {
                    _log.postValue(ErrorLogRecord(ErrorLogRecord.ERROR_PROJECT, errors = result.errors))
                }

                result.testResults?.let {
                    _log.postValue(TestResultsLogRecord(it))
                }
            }
            is Error -> {
                _log.postValue(ErrorLogRecord(ErrorLogRecord.ERROR_MESSAGE, resource.message))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveExecutor.release()
    }
}