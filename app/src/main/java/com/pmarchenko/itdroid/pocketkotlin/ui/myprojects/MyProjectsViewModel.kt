package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmarchenko.itdroid.pocketkotlin.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.repository.ProjectsRepository
import com.pmarchenko.itdroid.pocketkotlin.utils.LiveDataHolder
import com.pmarchenko.itdroid.pocketkotlin.utils.doInBackground

class MyProjectsViewModel(private val projectRepo: ProjectsRepository) : ViewModel() {

    val userProjects: LiveData<List<Project>> = projectRepo.userProjects

    private val _newProjectCreated = MutableLiveData<LiveDataHolder<Long?>>()
    val newProjectCreated: LiveData<LiveDataHolder<Long?>> = _newProjectCreated

    fun addNewProject(project: Project) {
        doInBackground {
            val projectId = projectRepo.addProject(project)
            _newProjectCreated.postValue(LiveDataHolder(projectId, -1L))
        }
    }

    fun deleteProject(project: Project) {
        doInBackground { projectRepo.deleteProject(project) }
    }

    fun updateProjectName(project: Project, name: String) {
        doInBackground { projectRepo.updateProject(project.copy(name = name)) }
    }
}