package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */
enum class ProjectType {

    UserProject,
    Example,
    ModifiedExample
}

internal fun DbProjectType.fromDbProjectType(): ProjectType =
    when (this) {
        DbProjectType.UserProject -> ProjectType.UserProject
        DbProjectType.Example -> ProjectType.Example
        DbProjectType.ModifiedExample -> ProjectType.ModifiedExample
    }

internal fun ProjectType.toDbProjectType(): DbProjectType =
    when (this) {
        ProjectType.UserProject -> DbProjectType.UserProject
        ProjectType.Example -> DbProjectType.Example
        ProjectType.ModifiedExample -> DbProjectType.ModifiedExample
    }