package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */
enum class ProjectType {
    USER_PROJECT,
    EXAMPLE
}

internal fun DbProjectType.fromDbProjectType(): ProjectType =
    when (this) {
        DbProjectType.USER_PROJECT -> ProjectType.USER_PROJECT
        DbProjectType.EXAMPLE -> ProjectType.EXAMPLE
    }

internal fun ProjectType.toDbProjectType(): DbProjectType =
    when (this) {
        ProjectType.USER_PROJECT -> DbProjectType.USER_PROJECT
        ProjectType.EXAMPLE -> DbProjectType.EXAMPLE
    }