package com.itdroid.pocketkotlin.database.entity

/**
 * @author itdroid
 */
enum class ProjectStatus {

    Default,
    SoftDeleted;

    /**
     * Keep in sync with [ProjectStatus] enum names
     * */
    companion object {

        @Suppress("unused")
        const val DB_DEFAULT = "Default"

        const val DB_SOFT_DELETED = "SoftDeleted"
    }
}