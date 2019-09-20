package com.pmarchenko.itdroid.pocketkotlin.model.project

import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.JsonAdapter
import com.pmarchenko.itdroid.pocketkotlin.db.ProjectConverter
import com.pmarchenko.itdroid.pocketkotlin.db.ProjectTable
import com.pmarchenko.itdroid.pocketkotlin.db.entity.ProjectType
import com.pmarchenko.itdroid.pocketkotlin.network.ProjectFilesJsonSerialized
import kotlinx.android.parcel.Parcelize

/**
 * @author Pavel Marchenko
 */
@Entity(tableName = ProjectTable.TABLE)
@TypeConverters(ProjectConverter::class)
@Parcelize
data class Project(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ProjectTable.ID)
    val id: Long = 0L,

    @ColumnInfo(name = ProjectTable.PROJECT_TYPE)
    val projectType: ProjectType,

    @ColumnInfo(name = ProjectTable.NAME)
    val name: String,

    @ColumnInfo(name = ProjectTable.LANGUAGE, typeAffinity = ColumnInfo.TEXT)
    val language: Language = Language.KOTLIN,

    @ColumnInfo(name = ProjectTable.MAIN_FILE)
    var mainFile: String,

    @ColumnInfo(name = ProjectTable.SEARCH_FOR_MAIN)
    val searchForMain: Boolean,

    @ColumnInfo(name = ProjectTable.DATE_CREATED)
    val dateCreated: Long = System.currentTimeMillis(),

    @ColumnInfo(name = ProjectTable.DATE_MODIFIED)
    var dateModified: Long = System.currentTimeMillis(),

    @ColumnInfo(name = ProjectTable.ARGS)
    var args: String = "",

    @ColumnInfo(name = ProjectTable.COMPILER_VERSION)
    var compilerVersion: String? = null,

    @ColumnInfo(name = ProjectTable.EXECUTION_TYPE)
    val executionType: String = "run",

    @ColumnInfo(name = ProjectTable.RUN_CONFIG)
    val runConfig: String = "java"

) : Parcelable {
    @Ignore
    @JsonAdapter(ProjectFilesJsonSerialized::class)
    var files: MutableList<ProjectFile> = mutableListOf()

    fun mainFile() = files.first { it.name == mainFile }

    companion object {

        fun empty(name: String): Project {
            val timestamp = System.currentTimeMillis()
            val project = Project(
                projectType = ProjectType.USER_PROJECT,
                name = name,
                mainFile = "Main.kt",
                searchForMain = true,
                dateCreated = timestamp,
                dateModified = timestamp
            )
            project.files.add(
                ProjectFile(
                    publicId = "Main",
                    name = "Main.kt",
                    dateCreated = timestamp,
                    dateModified = timestamp
                )
            )
            return project
        }

        fun withMainFun(name: String): Project {
            val project = empty(name)
            project.files[0].program= """
                /**
                 * Program entry point
                 */
                fun main(args: Array<String>) {
                    
                }
            """.trimIndent()
            return project
        }

        fun helloWorld(name: String): Project {
            val project = empty(name)
            project.files[0].program = """
                /**
                 * Hello, World!
                 */
                fun main(args: Array<String>) {
                    println("Hello, World!")
                }
            """.trimIndent()
            return project
        }
    }
}