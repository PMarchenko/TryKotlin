package com.pmarchenko.itdroid.pocketkotlin.projects.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Pavel Marchenko
 */
@Parcelize
data class ProjectFile internal constructor(
    val id: Long = 0L,
    var projectId: Long = 0L,
    val publicId: String,
    val name: String,
    var dateCreated: Long = 0L,
    var dateModified: Long = 0L,
    var program: String = ""
): Parcelable

internal fun DbProjectFile.fromDbProjectFile(
    id: Long = this.id,
    projectId: Long = this.projectId,
    publicId: String = this.publicId,
    name: String = this.name,
    dateCreated: Long = this.dateCreated,
    dateModified: Long = this.dateModified,
    program: String = this.program
): ProjectFile =
    ProjectFile(
        id = id,
        projectId = projectId,
        publicId = publicId,
        name = name,
        dateCreated = dateCreated,
        dateModified = dateModified,
        program = program
    )

internal fun ProjectFile.toDbProjectFile(
    id: Long = this.id,
    projectId: Long = this.projectId,
    publicId: String = this.publicId,
    name: String = this.name,
    dateCreated: Long = this.dateCreated,
    dateModified: Long = this.dateModified,
    program: String = this.program
): DbProjectFile =
    DbProjectFile(
        id = id,
        projectId = projectId,
        publicId = publicId,
        name = name,
        dateCreated = dateCreated,
        dateModified = dateModified,
        program = program
    )