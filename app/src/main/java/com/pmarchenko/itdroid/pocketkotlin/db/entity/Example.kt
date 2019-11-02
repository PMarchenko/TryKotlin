package com.pmarchenko.itdroid.pocketkotlin.db.entity

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.pmarchenko.itdroid.pocketkotlin.db.ExamplesTable
import com.pmarchenko.itdroid.pocketkotlin.db.ProjectsTable

/**
 * @author Pavel Marchenko
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
    @Expose
    @SerializedName("category")
    val category: String,

    @ColumnInfo(name = ExamplesTable.DESCRIPTION)
    @Expose
    @SerializedName("description")
    val description: String,

    @Ignore
    @Expose
    @SerializedName("project")
    var exampleProject: Project?,

    @Ignore
    var modifiedProject: Project?
) {
    constructor(id: Long, exampleProjectId: Long, modifiedProjectId: Long, category: String, description: String) : this(
        id,
        exampleProjectId,
        modifiedProjectId,
        category,
        description,
        null,
        null
    )
}