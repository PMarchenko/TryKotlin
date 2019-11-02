package com.pmarchenko.itdroid.pocketkotlin.db.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.pmarchenko.itdroid.pocketkotlin.db.ExamplesTable
import com.pmarchenko.itdroid.pocketkotlin.db.ProjectsTable

/**
 * @author Pavel Marchenko
 */
data class ExampleWithProjects(

    @Embedded
    val example: Example,

    @Relation(entity = Project::class, parentColumn = ExamplesTable.EXAMPLE_PROJECT_ID, entityColumn = ProjectsTable.ID)
    val exampleProjectWithFiles: ProjectWithFiles,

    @Relation(entity = Project::class, parentColumn = ExamplesTable.MODIFIED_PROJECT_ID, entityColumn = ProjectsTable.ID)
    val modifiedProjectWithFiles: ProjectWithFiles
)
