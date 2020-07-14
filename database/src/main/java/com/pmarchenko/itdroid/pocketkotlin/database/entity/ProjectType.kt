package com.pmarchenko.itdroid.pocketkotlin.database.entity

/**
 * {@link ProjectType} and {@link ProjectType#} must be consistent
 *
 * @author Pavel Marchenko
 */
enum class ProjectType {

    USER_PROJECT, EXAMPLE;

    companion object {

        const val DB_USER_PROJECT = "USER_PROJECT"

        const val DB_EXAMPLE = "EXAMPLE"
    }
}

