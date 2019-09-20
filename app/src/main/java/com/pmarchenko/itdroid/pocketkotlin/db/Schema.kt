package com.pmarchenko.itdroid.pocketkotlin.db

import android.provider.BaseColumns

/**
 * @author Pavel Marchenko
 */
object AppDatabaseInfo {

    const val VERSION = 1

    const val NAME = "PocketKotlinDB"

}


object ProjectTable {

    const val TABLE = "Project"

    const val ID = BaseColumns._ID

    const val PROJECT_TYPE = "ProjectType"

    const val NAME = "Name"

    const val LANGUAGE = "Language"

    const val MAIN_FILE = "MainFile"

    const val SEARCH_FOR_MAIN = "searchForMain"

    const val DATE_CREATED = "CreatedTimestamp"

    const val DATE_MODIFIED = "ModifiedTimestamp"

    const val ARGS = "Args"

    const val COMPILER_VERSION = "CompilerVersion"

    const val EXECUTION_TYPE = "ExecutionType"

    const val RUN_CONFIG = "RunConfig"

}

object FileTable {

    const val TABLE = "File"

    const val ID = BaseColumns._ID

    const val PROJECT_ID = "ProjectId"

    const val PUBLIC_ID = "PublicId"

    const val NAME = "Name"

    const val DATE_CREATED = "CreatedTimestamp"

    const val DATE_MODIFIED = "ModifiedTimestamp"

    const val PROGRAM = "Program"
}