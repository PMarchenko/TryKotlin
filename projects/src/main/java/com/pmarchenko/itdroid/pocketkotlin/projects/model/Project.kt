package com.pmarchenko.itdroid.pocketkotlin.projects.model

import android.os.Parcelable
import com.pmarchenko.itdroid.pocketkotlin.network.model.File
import kotlinx.android.parcel.Parcelize

/**
 * @author Pavel Marchenko
 */
@Parcelize
data class Project(
    val id: Long = 0L,
    val publicId: String? = null,
    var projectType: ProjectType,
    val name: String,
    var mainFile: String,
    val searchForMain: Boolean,
    val dateCreated: Long = 0,
    var dateModified: Long = 0,
    var args: String = "",
    var compilerVersion: String? = null,
    val executionType: String = "run",
    val runConfig: String = "java",
    val originUrl: String? = null,
    var readOnlyFileNames: List<String> = listOf(),
    var files: MutableList<ProjectFile> = mutableListOf()
) : Parcelable

internal fun DbProject.fromDbProject(
    id: Long = this.id,
    publicId: String? = this.publicId,
    projectType: ProjectType = this.projectType.fromDbProjectType(),
    name: String = this.name,
    mainFile: String = this.mainFile,
    searchForMain: Boolean = this.searchForMain,
    dateCreated: Long = this.dateCreated,
    dateModified: Long = this.dateModified,
    args: String = this.args,
    compilerVersion: String? = this.compilerVersion,
    executionType: String = this.executionType,
    runConfig: String = this.runConfig,
    originUrl: String? = this.originUrl,
    readOnlyFileNames: List<String> = this.readOnlyFileNames,
    files: MutableList<ProjectFile> = this.files.mapTo(mutableListOf()) { it.fromDbProjectFile() }
): Project =
    Project(
        id = id,
        publicId = publicId,
        projectType = projectType,
        name = name,
        mainFile = mainFile,
        searchForMain = searchForMain,
        dateCreated = dateCreated,
        dateModified = dateModified,
        args = args,
        compilerVersion = compilerVersion,
        executionType = executionType,
        runConfig = runConfig,
        originUrl = originUrl,
        readOnlyFileNames = readOnlyFileNames,
        files = files
    )

internal fun Project.toDbProject(
    id: Long = this.id,
    publicId: String? = this.publicId,
    projectType: DbProjectType = this.projectType.toDbProjectType(),
    name: String = this.name,
    mainFile: String = this.mainFile,
    searchForMain: Boolean = this.searchForMain,
    dateCreated: Long = this.dateCreated,
    dateModified: Long = this.dateModified,
    args: String = this.args,
    compilerVersion: String? = this.compilerVersion,
    executionType: String = this.executionType,
    runConfig: String = this.runConfig,
    originUrl: String? = this.originUrl,
    readOnlyFileNames: List<String> = this.readOnlyFileNames,
    files: List<DbProjectFile> = this.files.map { it.toDbProjectFile() }
): DbProject =
    DbProject(
        id = id,
        publicId = publicId,
        projectType = projectType,
        name = name,
        mainFile = mainFile,
        searchForMain = searchForMain,
        dateCreated = dateCreated,
        dateModified = dateModified,
        args = args,
        compilerVersion = compilerVersion,
        executionType = executionType,
        runConfig = runConfig,
        originUrl = originUrl,
        readOnlyFileNames = readOnlyFileNames
    ).also {
        it.files.addAll(files)
    }


internal fun Project.toApiProject(): ApiProject {
    return ApiProject(
        id = publicId,
        name = name,
        args = args,
        confType = runConfig,
        originUrl = originUrl,
        files = files.map {
            File(
                publicId = it.publicId,
                name = it.name,
                text = it.program
            )
        },
        readOnlyFileNames = readOnlyFileNames
    )
}