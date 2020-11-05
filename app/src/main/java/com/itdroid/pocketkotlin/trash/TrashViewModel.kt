package com.itdroid.pocketkotlin.trash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.itdroid.pocketkotlin.projects.ProjectsRepository
import com.itdroid.pocketkotlin.projects.model.Project
import com.itdroid.pocketkotlin.ui.compose.state.UiState

/**
 * @author itdroid
 */
class TrashViewModel(app: Application) : AndroidViewModel(app) {

    private val projectRepo = ProjectsRepository.instance(app)

    val uiState: LiveData<UiState<List<Project>>> = projectRepo.projectsInTrash
        .map { projects -> UiState(projects) }

}
