package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.adapter

import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.ProjectCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentAdapter
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.HolderDelegate

/**
 * @author Pavel Marchenko
 */
class MyProjectsAdapter(projectListCallback: ProjectCallback) : ContentAdapter() {

    override val delegates: Map<Int, HolderDelegate<HolderDelegateProject.ProjectViewHolder, ProjectContentData>> =
        mapOf(
            VIEW_TYPE_PROJECT to HolderDelegateProject(projectListCallback)
        )

    fun setProjects(projects: List<Project>) {
        setContent(projects.map { ProjectContentData(VIEW_TYPE_PROJECT, it) })
    }

    companion object {
        private const val VIEW_TYPE_PROJECT = 0
    }
}
