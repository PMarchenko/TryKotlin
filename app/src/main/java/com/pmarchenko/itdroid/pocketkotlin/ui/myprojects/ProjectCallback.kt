package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects

import android.view.View
import com.pmarchenko.itdroid.pocketkotlin.model.project.Project

/**
 * @author Pavel Marchenko
 */
interface ProjectCallback : ProjectNameCallback {

    fun onProjectClick(project: Project)

    fun onProjectMenuClick(anchor: View, project: Project)

    fun onAddProject(project: Project)

}