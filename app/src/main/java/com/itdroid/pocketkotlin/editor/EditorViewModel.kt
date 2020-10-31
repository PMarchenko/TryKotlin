package com.itdroid.pocketkotlin.editor

import android.app.Application
import android.text.Spannable
import androidx.lifecycle.*
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.ui.compose.state.UiState
import com.itdroid.pocketkotlin.ui.compose.state.defaultUiState
import com.itdroid.pocketkotlin.editor.logs.*
import com.itdroid.pocketkotlin.projects.ProjectsRepository
import com.itdroid.pocketkotlin.projects.model.*
import com.itdroid.pocketkotlin.utils.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * @author itdroid
 */
class EditorViewModel(private val app: Application) : AndroidViewModel(app) {

    private val projectsRepo = ProjectsRepository.instance(app)
    private val saveExecutor: ThrottleExecutor = ThrottleExecutor.forScope(viewModelScope)

    private val _appLogger = AppLogger(SpannableLogsProcessor(app))
    val appLogger: LiveData<List<Spannable>> = _appLogger

    private var pendingSelectionFileId: Long = -1L

    private val refreshHook = MutableLiveData<Long>()
    private val isLoading = MutableLiveData(false)
    private val isExecuting = MutableLiveData(false)
    private val projectId = MutableLiveData(-1L)
    private val project = projectId
        .switchMap { projectId ->
            projectsRepo.projectFlow(projectId)
                .distinctUntilChanged { old, new -> isProjectContentTheSame(old, new) }
                .asLiveData()

        } as MutableLiveData

    val uiState: LiveData<UiState<EditorInfo>> = MediatorLiveData<UiState<EditorInfo>>()
        .apply {
            value = defaultUiState()

            addSource(project) { project ->
                val editorInfo = value!!.data
                val selectedFileId = calculateFileId(project, editorInfo?.selectedFileId ?: -1L)
                val editorInfoUpdate =
                    if (project != null) {
                        editorInfo
                            ?.copy(project = project, selectedFileId = selectedFileId)
                            ?: EditorInfo(project = project, selectedFileId = selectedFileId)
                    } else {
                        null
                    }

                value = value!!.copy(data = editorInfoUpdate, isLoading = false)
            }
            addSource(isLoading) { isLoading ->
                value = value!!.copy(isLoading = isLoading)
            }
            addSource(isExecuting) { isExecuting ->
                val editorInfo = value!!.data
                if (editorInfo != null) {
                    value = value!!.copy(data = editorInfo.copy(isExecuting = isExecuting))
                }
            }
            addSource(refreshHook) {
                val editorInfo = value!!.data
                if (editorInfo != null) {
                    val project = editorInfo.project
                    val selectedFileId = calculateFileId(project, editorInfo.selectedFileId)
                    val refreshedEditorInfo =
                        editorInfo.copy(project = project, selectedFileId = selectedFileId)

                    value = value!!.copy(data = refreshedEditorInfo)
                }
            }
        }.distinctUntilChanged()

    fun loadProject(id: Long, fileId: Long = -1L) {
        if (projectId.value != id) {
            setLoading()
            pendingSelectionFileId = fileId
            projectId.value = id
        } else {
            selectFile(fileId)
        }
    }

    fun edit(project: Project, file: ProjectFile) {
        saveExecutor.post {
            val updated =
                if (project.type == ProjectType.Example) {
                    project.copy(
                        name = app.getString(
                            R.string.editor__example_project_name_pattern,
                            project.name
                        ),
                        type = ProjectType.ModifiedExample
                    )
                } else project

            projectsRepo.update(updated, file)
        }
    }

    fun executeProject(project: Project) {
        viewModelScope.launch {
            projectsRepo.execute(project)
                .onEach { resource -> logResource(resource, project) }
                .collect {
                    when (it) {
                        Loading -> setExecuting(true)
                        is SuccessResult<*>, is FailureResult -> setExecuting(false)
                    }.checkAllMatched
                }
        }
    }

    fun clearLogs() {
        _appLogger.clearLogs()
    }

    fun selectFile(id: Long) {
        pendingSelectionFileId = id
        refreshHook.value = System.currentTimeMillis()
    }

    fun addFile(project: Project, name: String, type: FileType) {
        viewModelScope.launch {
            pendingSelectionFileId = projectsRepo.addFile(project, name, type)
        }
    }

    private fun logResource(resource: Resource, project: Project) {
        when (resource) {
            Loading -> _appLogger.log(ExecuteLogRecord(project.name, project.args))
            is FailureResult -> {
                _appLogger.log(ErrorLogRecord(ErrorType.Message, message = resource.message))
            }
            is SuccessResult<*> -> {
                val result = resource.data as ProjectExecutionResult

                _appLogger.log(InfoLogRecord(result.text ?: ""))

                result.exception?.let { _appLogger.log(ExceptionLogRecord(it)) }

                if (result.hasErrors) {
                    _appLogger.log(ErrorLogRecord(ErrorType.Project, errors = result.errors))
                }

                if (result.testResults.isNotEmpty()) {
                    _appLogger.log(TestResultsLogRecord(result.testResults))
                }
            }
        }
    }

    private fun setLoading() {
        if (isLoading.value != true) {
            isLoading.value = true
        }
    }

    private fun setExecuting(executing: Boolean) {
        if (isExecuting.value != executing) {
            isExecuting.value = executing
        }
    }

    private fun calculateFileId(project: Project?, currentFileId: Long): Long {
        var id = currentFileId
        if (project != null && pendingSelectionFileId >= 0) {
            id = pendingSelectionFileId
            pendingSelectionFileId = -1L
        }

        return if (project?.files?.firstOrNull { it.id == id } != null) {
            id
        } else {
            project?.files?.firstOrNull()?.id ?: -1L
        }
    }

    private fun isProjectContentTheSame(old: Project?, new: Project?): Boolean {
        if (old == null || new == null) return false
        if (old == new) return true

        if (old.files.size != new.files.size) return false

        if (old.copy(dateModified = 0L, files = mutableListOf()) !=
            new.copy(dateModified = 0L, files = mutableListOf())
        ) {
            return false
        }

        for ((index, file) in old.files.withIndex()) {
            if (file.copy(dateModified = 0L) !=
                new.files[index].copy(dateModified = 0L)
            ) {
                return false
            }
        }

        return true
    }
}

private val ProjectExecutionResult.hasErrors: Boolean
    get() = errors.any { it.value.isNotEmpty() }
