package com.pmarchenko.itdroid.pocketkotlin.projects.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Pavel Marchenko
 */

/**
 * @author Pavel Marchenko
 */
@Parcelize
data class Example(
    val id: Long = 0L,
    val category: String,
    val description: String,
    val exampleProject: Project,
    val modifiedProject: Project
) : Parcelable

internal fun DbExample.fromDbExample(
    id: Long = this.id,
    category: String = this.category,
    description: String = this.description,
    exampleProject: Project = this.exampleProject!!.fromDbProject(),
    modifiedProject: Project = this.modifiedProject!!.fromDbProject()
): Example =
    Example(
        id = id,
        category = category,
        description = description,
        exampleProject = exampleProject,
        modifiedProject = modifiedProject
    )

internal fun Example.toDbExample(
    id: Long = this.id,
    category: String = this.category,
    description: String = this.description,
    exampleProject: DbProject = this.exampleProject.toDbProject(),
    modifiedProject: DbProject = this.modifiedProject.toDbProject()
): DbExample =
    DbExample(
        id = id,
        category = category,
        description = description,
        exampleProjectId = exampleProject.id,
        modifiedProjectId = modifiedProject.id
    ).also {
        it.exampleProject = exampleProject
        it.modifiedProject = modifiedProject
    }