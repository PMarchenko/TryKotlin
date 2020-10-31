package com.itdroid.pocketkotlin.database.entity

import androidx.room.*

/**
 * @author itdroid
 */
@Entity(tableName = ProjectsTable.TABLE)
@TypeConverters(ProjectConverter::class)
data class Project(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ProjectsTable.ID)
    val id: Long = 0L,

    @ColumnInfo(name = ProjectsTable.PUBLIC_ID)
    val publicId: String? = null,

    @ColumnInfo(name = ProjectsTable.PROJECT_TYPE)
    var projectType: ProjectType,

    @ColumnInfo(name = ProjectsTable.STATUS)
    var projectStatus: ProjectStatus = ProjectStatus.Default,

    @ColumnInfo(name = ProjectsTable.NAME)
    val name: String,

    @ColumnInfo(name = ProjectsTable.MAIN_FILE)
    var mainFile: String,

    @ColumnInfo(name = ProjectsTable.SEARCH_FOR_MAIN)
    val searchForMain: Boolean,

    @ColumnInfo(name = ProjectsTable.DATE_CREATED)
    val dateCreated: Long = 0,

    @ColumnInfo(name = ProjectsTable.DATE_MODIFIED)
    var dateModified: Long = 0,

    @ColumnInfo(name = ProjectsTable.DATE_SOFT_DELETED)
    var dateSoftDeleted: Long = 0,

    @ColumnInfo(name = ProjectsTable.ARGS)
    var args: String = "",

    @ColumnInfo(name = ProjectsTable.COMPILER_VERSION)
    var compilerVersion: String? = null,

    @ColumnInfo(name = ProjectsTable.EXECUTION_TYPE)
    val executionType: String = "run",

    @ColumnInfo(name = ProjectsTable.RUN_CONFIG)
    val runConfig: String = "java",

    @ColumnInfo(name = ProjectsTable.ORIGINAL_URL)
    val originUrl: String? = null,

    @ColumnInfo(name = ProjectsTable.READ_ONLY_FILE_NAMES)
    var readOnlyFileNames: List<String> = listOf(),
) {

    /**
     * Room does not support properties with [@Ignore] annotation in primary constructor
     * */
    @Ignore
    var files: MutableList<ProjectFile> = mutableListOf()

}