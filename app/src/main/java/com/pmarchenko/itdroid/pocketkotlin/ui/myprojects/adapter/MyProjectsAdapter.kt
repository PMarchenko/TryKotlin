package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.adapter

import com.pmarchenko.itdroid.pocketkotlin.model.project.Project
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.ProjectCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentAdapter
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.HolderDelegateManager

/**
 * @author Pavel Marchenko
 */
class MyProjectsAdapter(projectListCallback: ProjectCallback) : ContentAdapter(DelegateManager(projectListCallback)) {

    fun setProjects(projects: List<Project>) {
        val content = projects.map {
            ProjectContentData(VIEW_TYPE_PROJECT, it)
        }

        setContent(content)
    }

    companion object {
        const val VIEW_TYPE_PROJECT = 0
    }

    class DelegateManager(projectListCallback: ProjectCallback) : HolderDelegateManager() {

        init {
            register(HolderDelegateProject(VIEW_TYPE_PROJECT, projectListCallback))
        }
    }
}