package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter

import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectError
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.recycler.ContentData
import java.util.*

data class FileContentData(
        override val viewType: Int,
        val file: ProjectFile,
        val errors: ArrayList<ProjectError>,
        var selection: Pair<Int, Int>? //consumed by holder
) : ContentData {
    override fun isItemTheSame(data: ContentData): Boolean {
        if (data !is FileContentData) return false
        return file == data.file
    }

    override fun isContentTheSame(data: ContentData): Boolean {
        if (data !is FileContentData) return false
        return file == data.file && errors == data.errors && selection == data.selection
    }
}