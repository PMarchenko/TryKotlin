package com.pmarchenko.itdroid.pocketkotlin.ui.examples

import android.view.View
import com.pmarchenko.itdroid.pocketkotlin.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.ProjectCallback

/**
 * @author Pavel Marchenko
 */
open class ProjectCallbackAdapter: ProjectCallback {

    override fun onProjectClick(project: Project) {

    }

    override fun onProjectMenuClick(anchor: View, project: Project) {

    }

    override fun onAddProject(project: Project) {

    }

    override fun onProjectName(project: Project, name: String) {

    }
}