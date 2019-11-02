package com.pmarchenko.itdroid.pocketkotlin.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.pmarchenko.itdroid.pocketkotlin.db.FilesTable
import com.pmarchenko.itdroid.pocketkotlin.db.ProjectsTable

/**
 * @author Pavel Marchenko
 */
data class ProjectWithFiles(

    @Embedded
    val project: Project,

    @Relation(entity = ProjectFile::class, parentColumn = ProjectsTable.ID, entityColumn = FilesTable.PROJECT_ID)
    val files: List<ProjectFile>
)
