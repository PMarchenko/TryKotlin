package com.itdroid.pocketkotlin.database.entity

/**
 * {@link ProjectType} and {@link ProjectType#} must be consistent
 *
 * @author itdroid
 */
enum class FileType {

    File,
    Class,
    DataClass,
    Interface,
    Enum,
    Object;

    /**
     * Keep in sync with [FileType] enum names
     * */
    companion object {

        const val DB_USER_PROJECT = "UserProject"

        const val DB_EXAMPLE = "Example"

        const val DB_MODIFIED_EXAMPLE = "ModifiedExample"
    }
}

