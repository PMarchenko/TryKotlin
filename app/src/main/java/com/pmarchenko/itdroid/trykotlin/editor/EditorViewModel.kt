package com.pmarchenko.itdroid.trykotlin.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pmarchenko.itdroid.trykotlin.model.ProjectExecutionResult
import com.pmarchenko.itdroid.trykotlin.model.Project
import com.pmarchenko.itdroid.trykotlin.model.Resource
import com.pmarchenko.itdroid.trykotlin.repository.ProjectRepository

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
class EditorViewModel : ViewModel() {

    private val programRepository = ProjectRepository()

    private val programExecutor = MutableLiveData<Project>()

    val executionResult: LiveData<Resource<ProjectExecutionResult>> = Transformations.switchMap(programExecutor) {
        programRepository.execute(it)
    }

    fun executeProgram(project: Project) {
        programExecutor.value = project
    }
}