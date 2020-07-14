package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects

import android.app.Application
import androidx.lifecycle.*
import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project
import com.pmarchenko.itdroid.pocketkotlin.projects.ProjectsRepository
import com.pmarchenko.itdroid.pocketkotlin.ui.Event
import kotlinx.coroutines.launch

class MyProjectsViewModel(val app: Application) : AndroidViewModel(app) {

    private val projectRepo = ProjectsRepository.newInstance(app)

    private val _viewState = projectRepo.userProjects
        .map {
            MyProjectsViewState(projects = it)
        } as MutableLiveData

    val viewState: LiveData<MyProjectsViewState> by lazy {
        _viewState.value = MyProjectsViewState(true)
        _viewState
    }

    private val _openProject = MutableLiveData<Event<Long>>()
    val openProject: LiveData<Event<Long>> = _openProject

    fun addNewProject(project: Project) {
        viewModelScope.launch {
            val projectId = projectRepo.addProject(project)
            _openProject.postValue(Event(projectId))
        }
    }

    fun deleteProject(project: Project) {
        viewModelScope.launch { projectRepo.deleteProject(project) }
    }
}

