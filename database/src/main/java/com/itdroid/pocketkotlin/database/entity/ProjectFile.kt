package com.itdroid.pocketkotlin.database.entity

import androidx.room.*

/**
 * @author itdroid
 */
@Entity(
    tableName = FilesTable.TABLE,
    foreignKeys = [
        ForeignKey(
            entity = Project::class,
            parentColumns = [ProjectsTable.ID],
            childColumns = [FilesTable.PROJECT_ID],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(FilesTable.PROJECT_ID)]
)
@TypeConverters(FileConverter::class)
data class ProjectFile(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = FilesTable.ID)
    val id: Long = 0L,

    @ColumnInfo(name = FilesTable.PROJECT_ID)
    var projectId: Long = 0L,

    @ColumnInfo(name = FilesTable.PUBLIC_ID)
    val publicId: String,

    @ColumnInfo(name = FilesTable.TYPE)
    val type: FileType = FileType.File,

    @ColumnInfo(name = FilesTable.NAME)
    val name: String,

    @ColumnInfo(name = FilesTable.DATE_CREATED, defaultValue = "CURRENT_TIMESTAMP")
    var dateCreated: Long = 0L,

    @ColumnInfo(name = FilesTable.DATE_MODIFIED, defaultValue = "CURRENT_TIMESTAMP")
    var dateModified: Long = 0L,

    @ColumnInfo(name = FilesTable.PROGRAM)
    var program: String = ""

)