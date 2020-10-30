package com.pmarchenko.itdroid.pocketkotlin.myprojects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.pmarchenko.itdroid.pocketkotlin.projects.ProjectsRepository
import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project
import com.pmarchenko.itdroid.pocketkotlin.compose.state.UiState

class MyProjectsViewModel(app: Application) : AndroidViewModel(app) {

    private val projectsRepo = ProjectsRepository.instance(app)

    val state: LiveData<UiState<List<Project>>> = projectsRepo
        .userProjects
        .map { UiState(it) }
}
