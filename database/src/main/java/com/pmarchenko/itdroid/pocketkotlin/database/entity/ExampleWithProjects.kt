package com.pmarchenko.itdroid.pocketkotlin.database.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * @author Pavel Marchenko
 */
internal data class ExampleWithProjects(

    @Embedded
    val example: Example,

    @Relation(
        entity = Project::class,
        parentColumn = ExamplesTable.EXAMPLE_PROJECT_ID,
        entityColumn = ProjectsTable.ID
    )
    val exampleProjectWithFiles: ProjectWithFiles,

    @Relation(
        entity = Project::class,
        parentColumn = ExamplesTable.MODIFIED_PROJECT_ID,
        entityColumn = ProjectsTable.ID
    )
    val modifiedProjectWithFiles: ProjectWithFiles
)
