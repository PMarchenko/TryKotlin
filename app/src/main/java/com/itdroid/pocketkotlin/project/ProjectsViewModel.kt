package com.itdroid.pocketkotlin.project

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.itdroid.pocketkotlin.navigation.Destination
import com.itdroid.pocketkotlin.navigation.ProjectEditorDestination
import com.itdroid.pocketkotlin.projects.ProjectsRepository
import com.itdroid.pocketkotlin.projects.model.Project
import com.itdroid.pocketkotlin.projects.model.ProjectFile
import com.itdroid.pocketkotlin.projects.model.ProjectStatus
import com.itdroid.pocketkotlin.projects.model.ProjectType
import kotlinx.coroutines.launch

/**
 * @author itdroid
 */
class ProjectsViewModel(app: Application) : AndroidViewModel(app) {

    private val projectsRepo = ProjectsRepository.instance(app)

    fun add(name: String, withMain: Boolean, navigation: (Destination) -> Unit) {
        viewModelScope.launch {
            val projectId = projectsRepo.addProject(name, withMain)
            navigation(ProjectEditorDestination(projectId))
        }
    }

    fun update(project: Project) {
        viewModelScope.launch { projectsRepo.update(project) }
    }

    fun delete(project: Project) {
        viewModelScope.launch { projectsRepo.delete(project) }
    }

    fun restore(project: Project) {
        viewModelScope.launch {
            projectsRepo.update(
                project.copy(
                    type = ProjectType.UserProject,
                    status = ProjectStatus.Default,
                    dateSoftDeleted = 0,
                    dateModified = System.currentTimeMillis()
                )
            )
        }
    }

    fun deleteForever(project: Project) {
        viewModelScope.launch {
            projectsRepo.deleteForever(project)
        }
    }

    fun delete(file: ProjectFile) {
        viewModelScope.launch {
            projectsRepo.delete(file)
        }
    }

    fun cleanupTrashBin() {
        viewModelScope.launch {
            projectsRepo.deleteForeverAll()
        }
    }
}