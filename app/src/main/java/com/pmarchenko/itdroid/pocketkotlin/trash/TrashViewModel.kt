package com.pmarchenko.itdroid.pocketkotlin.trash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.pmarchenko.itdroid.pocketkotlin.projects.ProjectsRepository
import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project
import com.pmarchenko.itdroid.pocketkotlin.compose.state.UiState

/**
 * @author Pavel Marchenko
 */
class TrashViewModel(app: Application) : AndroidViewModel(app) {

    private val projectRepo = ProjectsRepository.instance(app)

    val uiState: LiveData<UiState<List<Project>>> = projectRepo.projectsInTrash
        .map { projects -> UiState(projects) }

}
