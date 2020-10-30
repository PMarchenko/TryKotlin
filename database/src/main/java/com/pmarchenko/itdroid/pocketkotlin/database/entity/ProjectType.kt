package com.pmarchenko.itdroid.pocketkotlin.database.entity

/**
 * {@link ProjectType} and {@link ProjectType#} must be consistent
 *
 * @author Pavel Marchenko
 */
enum class ProjectType {

    UserProject,
    Example,
    ModifiedExample;

    /**
     * Keep in sync with [ProjectType] enum names
     * */
    companion object {

        const val DB_USER_PROJECT = "UserProject"

        const val DB_EXAMPLE = "Example"

        const val DB_MODIFIED_EXAMPLE = "ModifiedExample"
    }
}

