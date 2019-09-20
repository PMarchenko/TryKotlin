package com.pmarchenko.itdroid.pocketkotlin.db.entity

/**
 * {@link ProjectType} and {@link ProjectType#} must be consistent
 *
 * @author Pavel Marchenko
 */
enum class ProjectType {

    USER_PROJECT;

    companion object {

        const val DB_PROJECT = "USER_PROJECT"
    }
}

