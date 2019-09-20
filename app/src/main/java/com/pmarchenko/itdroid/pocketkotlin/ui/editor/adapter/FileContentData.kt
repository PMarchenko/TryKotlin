package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter

import com.pmarchenko.itdroid.pocketkotlin.model.project.Project
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectError
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentData

data class FileContentData(
    override val viewType: Int,
    val project: Project,
    val file: ProjectFile,
    val errors: List<ProjectError>,
    var selection: Pair<Int, Int>? //consumed by holder
) : ContentData {

    override val itemId = file.id

    override fun isItemTheSame(data: ContentData): Boolean {
        return if (data is FileContentData) {
            file.id == data.file.id
        } else false
    }

    override fun isContentTheSame(data: ContentData): Boolean {
        return if (data is FileContentData) {
            project == data.project
                    && file == data.file
                    && errors == data.errors
                    && selection == data.selection
        } else false
    }
}