package com.pmarchenko.itdroid.pocketkotlin.domain.db.entity

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.pmarchenko.itdroid.pocketkotlin.domain.db.FilesTable
import com.pmarchenko.itdroid.pocketkotlin.domain.db.ProjectsTable
import kotlinx.android.parcel.Parcelize

/**
 * @author Pavel Marchenko
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
@Parcelize
data class ProjectFile(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = FilesTable.ID)
    val id: Long = 0L,

    @ColumnInfo(name = FilesTable.PROJECT_ID)
    var projectId: Long = 0L,

    @ColumnInfo(name = FilesTable.PUBLIC_ID)
    @Expose
    val publicId: String,

    @ColumnInfo(name = FilesTable.NAME)
    @Expose
    val name: String,

    @ColumnInfo(name = FilesTable.DATE_CREATED, defaultValue = "CURRENT_TIMESTAMP")
    var dateCreated: Long = 0L,

    @ColumnInfo(name = FilesTable.DATE_MODIFIED, defaultValue = "CURRENT_TIMESTAMP")
    var dateModified: Long = 0L,

    @ColumnInfo(name = FilesTable.PROGRAM)
    @Expose
    @SerializedName("text")
    var program: String = ""

) : Parcelable