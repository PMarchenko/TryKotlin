package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.adapter

import com.pmarchenko.itdroid.pocketkotlin.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.ProjectCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentAdapter
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.HolderDelegate

/**
 * @author Pavel Marchenko
 */
class MyProjectsAdapter(private val projectListCallback: ProjectCallback) : ContentAdapter() {

    fun setProjects(projects: List<Project>) {
        val content = projects.map {
            ProjectContentData(VIEW_TYPE_PROJECT, it)
        }

        setContent(content)
    }

    override fun delegates(): Map<Int, HolderDelegate<*, *>> =
        mapOf(
            VIEW_TYPE_PROJECT to HolderDelegateProject(projectListCallback)
        )

    companion object {
        const val VIEW_TYPE_PROJECT = 0
    }
}