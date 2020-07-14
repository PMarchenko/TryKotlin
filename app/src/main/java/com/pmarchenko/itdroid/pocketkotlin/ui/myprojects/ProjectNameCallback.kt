package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects

import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project

/**
 * @author Pavel Marchenko
 */
interface ProjectNameCallback {

    fun onProjectName(project: Project, name: String)
}