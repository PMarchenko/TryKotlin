package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pmarchenko.itdroid.pocketkotlin.core.utils.ConsumableValue
import com.pmarchenko.itdroid.pocketkotlin.core.utils.doInBackground
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.domain.repository.ProjectsRepository

class MyProjectsViewModel(private val projectRepo: ProjectsRepository) : ViewModel() {

    val userProjects: LiveData<List<Project>> = projectRepo.userProjects

    private val _newProjectCreated = MutableLiveData<ConsumableValue<Long?>>()
    val newProjectCreated: LiveData<ConsumableValue<Long?>> = _newProjectCreated

    fun addNewProject(project: Project) {
        doInBackground {
            val projectId = projectRepo.addProject(project)
            _newProjectCreated.postValue(ConsumableValue(projectId, -1L))
        }
    }

    fun deleteProject(project: Project) {
        doInBackground { projectRepo.deleteProject(project) }
    }

    fun updateProjectName(project: Project, name: String) {
        doInBackground { projectRepo.updateProject(project.copy(name = name)) }
    }
}