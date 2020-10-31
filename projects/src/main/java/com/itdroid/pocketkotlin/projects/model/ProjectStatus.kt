package com.itdroid.pocketkotlin.projects.model

/**
 * @author itdroid
 */
enum class ProjectStatus {

    Default,
    SoftDeleter
}

internal fun DbProjectStatus.fromDbProjectStatus(): ProjectStatus =
    when (this) {
        DbProjectStatus.Default -> ProjectStatus.Default
        DbProjectStatus.SoftDeleted -> ProjectStatus.SoftDeleter
    }

internal fun ProjectStatus.toDbProjectType(): DbProjectStatus =
    when (this) {
        ProjectStatus.Default -> DbProjectStatus.Default
        ProjectStatus.SoftDeleter -> DbProjectStatus.SoftDeleted
    }