package com.pmarchenko.itdroid.pocketkotlin.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.pmarchenko.itdroid.pocketkotlin.db.FileTable
import com.pmarchenko.itdroid.pocketkotlin.db.ProjectTable
import com.pmarchenko.itdroid.pocketkotlin.model.project.Project
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectFile

/**
 * @author Pavel Marchenko
 */
data class ProjectWithFiles(

    @Embedded
    val project: Project,

    @Relation(entity = ProjectFile::class, parentColumn = ProjectTable.ID, entityColumn = FileTable.PROJECT_ID)
    val files: List<ProjectFile>
)
