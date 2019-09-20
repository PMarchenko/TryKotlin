package com.pmarchenko.itdroid.pocketkotlin.model.project

import androidx.room.*
import com.pmarchenko.itdroid.pocketkotlin.db.FileTable
import com.pmarchenko.itdroid.pocketkotlin.db.ProjectTable

/**
 * @author Pavel Marchenko
 */
@Entity(
    tableName = FileTable.TABLE,
    foreignKeys = [
        ForeignKey(entity = Project::class, parentColumns = [ProjectTable.ID], childColumns = [FileTable.PROJECT_ID], onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index(FileTable.PROJECT_ID)]
)
data class ProjectFile(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = FileTable.ID)
    val id: Long = 0L,

    @ColumnInfo(name = FileTable.PROJECT_ID)
    var projectId: Long = 0L, //dao or room must set this

    @ColumnInfo(name = FileTable.PUBLIC_ID)
    val publicId: String,

    @ColumnInfo(name = FileTable.NAME)
    val name: String,

    @ColumnInfo(name = FileTable.DATE_CREATED, defaultValue = "CURRENT_TIMESTAMP")
    val dateCreated: Long = System.currentTimeMillis(),

    @ColumnInfo(name = FileTable.DATE_MODIFIED, defaultValue = "CURRENT_TIMESTAMP")
    var dateModified: Long = System.currentTimeMillis(),

    @ColumnInfo(name = FileTable.PROGRAM)
    var program: String = ""
)