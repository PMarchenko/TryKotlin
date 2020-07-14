package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects

import android.view.View
import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project

/**
 * @author Pavel Marchenko
 */
interface ProjectCallback {

    fun onProjectClick(project: Project)

    fun onProjectMenuClick(anchor: View, project: Project)

}

fun projectClickCallback(callback: (Project) -> Unit): ProjectCallback {
    return object : ProjectCallback {
        override fun onProjectClick(project: Project) {
            callback(project)
        }

        override fun onProjectMenuClick(anchor: View, project: Project) {}
    }
}

fun ProjectCallbackMenuClick(callback: (View, Project) -> Unit): ProjectCallback {
    return object : ProjectCallback {
        override fun onProjectMenuClick(anchor: View, project: Project) {
            callback(anchor, project)
        }

        override fun onProjectClick(project: Project) {}
    }
}