package com.itdroid.pocketkotlin.projects.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author itdroid
 */
@Parcelize
data class Example internal constructor(
    val id: Long = 0L,
    val category: String,
    val categorySortOrder: Int,
    val sortOrder: Int,
    val exampleProject: Project,
    val modifiedProject: Project,
) : Parcelable

internal fun DbExample.fromDbExample(
    id: Long = this.id,
    category: String = this.category,
    categorySortOrder: Int = this.categorySortOrder,
    sortOrder: Int = this.sortOrder,
    exampleProject: Project = this.exampleProject!!.fromDbProject(),
    modifiedProject: Project = this.modifiedProject!!.fromDbProject(),
): Example =
    Example(
        id = id,
        category = category,
        categorySortOrder = categorySortOrder,
        sortOrder = sortOrder,
        exampleProject = exampleProject,
        modifiedProject = modifiedProject
    )

@Suppress("unused")
internal fun Example.toDbExample(
    id: Long = this.id,
    category: String = this.category,
    categorySortOrder: Int = this.categorySortOrder,
    sortOrder: Int = this.sortOrder,
    exampleProject: DbProject = this.exampleProject.toDbProject(),
    modifiedProject: DbProject = this.modifiedProject.toDbProject(),
): DbExample =
    DbExample(
        id = id,
        category = category,
        categorySortOrder = categorySortOrder,
        sortOrder = sortOrder,
        exampleProjectId = exampleProject.id,
        modifiedProjectId = modifiedProject.id
    ).also {
        it.exampleProject = exampleProject
        it.modifiedProject = modifiedProject
    }