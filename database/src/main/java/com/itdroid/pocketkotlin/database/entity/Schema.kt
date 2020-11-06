package com.itdroid.pocketkotlin.database.entity

import android.provider.BaseColumns

/**
 * @author itdroid
 */
internal object AppDatabaseInfo {

    const val VERSION = 1

    const val NAME = "PocketKotlinDB"

}

internal object ExamplesTable {

    const val TABLE = "Examples"

    const val ID = BaseColumns._ID

    const val EXAMPLE_PROJECT_ID = "ExampleProjectId"

    const val MODIFIED_PROJECT_ID = "ModifiedProjectId"

    const val CATEGORY = "Category"

    const val CATEGORY_SORT_ORDER = "CategorySortOrder"

    const val SORT_ORDER = "SortOrder"

}

internal object ProjectsTable {

    const val TABLE = "Project"

    const val ID = BaseColumns._ID

    const val PUBLIC_ID = "PublicId"

    const val PROJECT_TYPE = "ProjectType"

    const val NAME = "Name"

    const val MAIN_FILE = "MainFile"

    const val SEARCH_FOR_MAIN = "SearchForMain"

    const val DATE_CREATED = "CreatedTimestamp"

    const val DATE_MODIFIED = "ModifiedTimestamp"

    const val ARGS = "Args"

    const val COMPILER_VERSION = "CompilerVersion"

    const val EXECUTION_TYPE = "ExecutionType"

    const val RUN_CONFIG = "RunConfig"

    const val ORIGINAL_URL = "OriginalUrl"

    const val READ_ONLY_FILE_NAMES = "ReadOnlyFileNames"

    const val STATUS = "Status"

    const val DATE_SOFT_DELETED = "SoftDeletedTimestamp"

}

internal object FilesTable {

    const val TABLE = "File"

    const val ID = BaseColumns._ID

    const val PROJECT_ID = "ProjectId"

    const val PUBLIC_ID = "PublicId"

    const val TYPE = "Type"

    const val NAME = "Name"

    const val DATE_CREATED = "CreatedTimestamp"

    const val DATE_MODIFIED = "ModifiedTimestamp"

    const val PROGRAM = "Program"

}