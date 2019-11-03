package com.pmarchenko.itdroid.pocketkotlin.domain.db.entity

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.pmarchenko.itdroid.pocketkotlin.domain.db.ProjectConverter
import com.pmarchenko.itdroid.pocketkotlin.domain.db.ProjectsTable
import com.pmarchenko.itdroid.pocketkotlin.data.model.project.Language
import kotlinx.android.parcel.Parcelize

/**
 * @author Pavel Marchenko
 */
@Entity(tableName = ProjectsTable.TABLE)
@TypeConverters(ProjectConverter::class)
@Parcelize
data class Project(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ProjectsTable.ID)
    val id: Long = 0L,

    @SerializedName("id")
    @Expose
    @ColumnInfo(name = ProjectsTable.PUBLIC_ID)
    val publicId: String? = null,

    @ColumnInfo(name = ProjectsTable.PROJECT_TYPE)
    var projectType: ProjectType,

    @ColumnInfo(name = ProjectsTable.NAME)
    @Expose
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = ProjectsTable.LANGUAGE, typeAffinity = ColumnInfo.TEXT)
    val language: Language = Language.KOTLIN,

    @ColumnInfo(name = ProjectsTable.MAIN_FILE)
    var mainFile: String,

    @ColumnInfo(name = ProjectsTable.SEARCH_FOR_MAIN)
    @Expose
    @SerializedName("searchForMain")
    val searchForMain: Boolean,

    @ColumnInfo(name = ProjectsTable.DATE_CREATED)
    val dateCreated: Long = 0,

    @ColumnInfo(name = ProjectsTable.DATE_MODIFIED)
    var dateModified: Long = 0,

    @ColumnInfo(name = ProjectsTable.ARGS)
    @Expose
    @SerializedName("args")
    var args: String = "",

    @ColumnInfo(name = ProjectsTable.COMPILER_VERSION)
    var compilerVersion: String? = null,

    @ColumnInfo(name = ProjectsTable.EXECUTION_TYPE)
    val executionType: String = "run",

    @ColumnInfo(name = ProjectsTable.RUN_CONFIG)
    @Expose
    @SerializedName("confType")
    val runConfig: String = "java",

    @ColumnInfo(name = ProjectsTable.ORIGINAL_URL)
    @Expose
    @SerializedName("originUrl")
    val originalUrl: String? = null,

    @ColumnInfo(name = ProjectsTable.READ_ONLY_FILE_NAMES)
    @Expose
    @SerializedName("readOnlyFileNames")
    var readOnlyFileNames: List<String> = mutableListOf(),

    @Ignore
    @Expose
    @SerializedName("files")
    var files: MutableList<ProjectFile> = mutableListOf()

) : Parcelable {

    constructor(
        id: Long,
        publicId: String?,
        projectType: ProjectType,
        name: String,
        language: Language,
        mainFile: String,
        searchForMain: Boolean,
        dateCreated: Long,
        dateModified: Long,
        args: String,
        compilerVersion: String?,
        executionType: String,
        originalUrl: String?,
        runConfig: String
    ) : this(
        id,
        publicId,
        projectType,
        name,
        language,
        mainFile,
        searchForMain,
        dateCreated,
        dateModified,
        args,
        compilerVersion,
        executionType,
        runConfig,
        originalUrl,
        mutableListOf(),
        mutableListOf()
    )

    fun mainFile() = files.first { it.name == mainFile }

    companion object {

        fun empty(name: String): Project {
            val timestamp = System.currentTimeMillis()
            val project = Project(
                name = name,
                mainFile = "Main.kt",
                searchForMain = true,
                dateCreated = timestamp,
                projectType = ProjectType.USER_PROJECT
            )
            project.files.add(
                ProjectFile(
                    publicId = "Main",
                    name = "Main.kt",
                    dateCreated = timestamp
                )
            )
            return project
        }

        fun withMainFun(name: String): Project {
            val project = empty(name)
            project.files[0].program = """
                /**
                 * Program entry point
                 */
                fun main(args: Array<String>) {
                    
                }
            """.trimIndent()
            return project
        }
    }
}