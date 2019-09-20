package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import android.app.Application
import androidx.lifecycle.*
import com.pmarchenko.itdroid.pocketkotlin.db.AppDatabase
import com.pmarchenko.itdroid.pocketkotlin.model.Error
import com.pmarchenko.itdroid.pocketkotlin.model.Loading
import com.pmarchenko.itdroid.pocketkotlin.model.Resource
import com.pmarchenko.itdroid.pocketkotlin.model.Success
import com.pmarchenko.itdroid.pocketkotlin.model.log.LogLiveData
import com.pmarchenko.itdroid.pocketkotlin.model.log.LogRecord
import com.pmarchenko.itdroid.pocketkotlin.model.project.Project
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectExecutionResult
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.repository.ProjectsRepository
import com.pmarchenko.itdroid.pocketkotlin.utils.ThrottleTaskExecutor
import com.pmarchenko.itdroid.pocketkotlin.utils.async

/**
 * @author Pavel Marchenko
 */
class EditorViewModel(app: Application) : AndroidViewModel(app) {

    private val projectRepo = ProjectsRepository(AppDatabase.getDatabase(app).getProjectDao())

    private val projectExecutor = MutableLiveData<Project>()

    private val throttleExecutor = ThrottleTaskExecutor(tag = "ProjectThrottler")

    private val _log = LogLiveData()
    val log: LiveData<List<LogRecord>> = _log

    private val _projectId = MutableLiveData<Long>()
    private val _project: LiveData<Project> = Transformations.map(
        Transformations.switchMap(_projectId) { projectId ->
            projectRepo.loadProject(projectId)
        }
    ) { projectWithFiles ->
        val project = projectWithFiles.project
        project.files.addAll(projectWithFiles.files)
        project
    }

    private val _viewState = MediatorLiveData<EditorViewState>().apply {
        addSource(
            Transformations.switchMap(projectExecutor) { project ->
                projectRepo.execute(_log, project)
            }) { resource -> value = asState(resource) }

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
            async {
                project.args = args
                projectRepo.updateProject(project.copy(args = args))
            }
        }
    }

    fun editProjectFile(project: Project, file: ProjectFile, program: String) {
        file.program = program
        throttleExecutor.throttle {
            projectRepo.updateFile(project, file.copy(program = program))
        }
    }

    fun updateProjectName(project: Project, name: String) {
        async {
            projectRepo.updateProject(project.copy(name = name))
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

    override fun onCleared() {
        super.onCleared()
        throttleExecutor.release()
    }
}