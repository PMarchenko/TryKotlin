package com.itdroid.pocketkotlin.database.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * @author itdroid
 */
internal data class ProjectWithFiles(

    @Embedded
    val project: Project,

    @Relation(
        entity = ProjectFile::class,
        parentColumn = ProjectsTable.ID,
        entityColumn = FilesTable.PROJECT_ID
    )
    val files: List<ProjectFile>
)
