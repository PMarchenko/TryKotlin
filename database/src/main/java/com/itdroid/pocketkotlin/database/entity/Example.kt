package com.itdroid.pocketkotlin.database.entity

import androidx.room.*

/**
 * @author itdroid
 */
@Entity(
    tableName = ExamplesTable.TABLE,
    foreignKeys = [
        ForeignKey(
            entity = Project::class,
            parentColumns = [ProjectsTable.ID],
            childColumns = [ExamplesTable.EXAMPLE_PROJECT_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(ExamplesTable.EXAMPLE_PROJECT_ID)]
)
data class Example(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ExamplesTable.ID)
    val id: Long = 0L,

    @ColumnInfo(name = ExamplesTable.EXAMPLE_PROJECT_ID)
    var exampleProjectId: Long = 0L,

    @ColumnInfo(name = ExamplesTable.MODIFIED_PROJECT_ID)
    var modifiedProjectId: Long = 0L,

    @ColumnInfo(name = ExamplesTable.CATEGORY)
    val category: String,

    @ColumnInfo(name = ExamplesTable.DESCRIPTION)
    val description: String

) {

    /**
     * Room does not support properties with [@Ignore] annotation in primary constructor
     * */
    @Ignore
    var exampleProject: Project? = null

    /**
     * Room does not support properties with [@Ignore] annotation in primary constructor
     * */
    @Ignore
    var modifiedProject: Project? = null
}