package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.adapter

import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentData

/**
 * @author Pavel Marchenko
 */
class ProjectContentData(override val viewType: Int, val project: Project) : ContentData {

    override val itemId: Long = project.id

    override fun isItemTheSame(data: ContentData): Boolean {
        return if (data is ProjectContentData) {
            project.id == data.project.id
        } else {
            false
        }
    }

    override fun isContentTheSame(data: ContentData) = equals(data)

}
