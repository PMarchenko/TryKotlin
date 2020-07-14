package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects

import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project

/**
 * @author Pavel Marchenko
 */
data class MyProjectsViewState(
    val isLoading: Boolean = false,
    val projects: List<Project> = emptyList()
)