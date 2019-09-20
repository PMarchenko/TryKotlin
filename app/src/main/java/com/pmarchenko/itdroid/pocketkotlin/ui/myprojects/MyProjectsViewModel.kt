package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pmarchenko.itdroid.pocketkotlin.db.AppDatabase
import com.pmarchenko.itdroid.pocketkotlin.model.project.Project
import com.pmarchenko.itdroid.pocketkotlin.repository.ProjectsRepository
import com.pmarchenko.itdroid.pocketkotlin.utils.LiveDataHolder
import com.pmarchenko.itdroid.pocketkotlin.utils.async

class MyProjectsViewModel(app: Application) : AndroidViewModel(app) {

    private val projectRepo = ProjectsRepository(AppDatabase.getDatabase(app).getProjectDao())
    val myProjects: LiveData<List<Project>> = projectRepo.myProjects

    private val _newProjectCreated = MutableLiveData<LiveDataHolder<Long?>>()
    val newProjectCreated: LiveData<LiveDataHolder<Long?>> = _newProjectCreated

    fun addNewProject(project: Project) {
        async {
            val projectId = projectRepo.addProject(project)
            _newProjectCreated.postValue(LiveDataHolder(projectId, -1L))
        }
    }

    fun deleteProject(project: Project) {
        async { projectRepo.deleteProject(project) }
    }

    fun updateProjectName(project: Project, name: String) {
        async { projectRepo.updateProject(project.copy(name = name)) }
    }
}