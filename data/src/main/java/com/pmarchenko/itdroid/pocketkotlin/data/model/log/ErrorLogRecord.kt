package com.pmarchenko.itdroid.pocketkotlin.data.model.log

import com.pmarchenko.itdroid.pocketkotlin.data.model.project.ProjectError

/**
 * @author Pavel Marchenko
 */
data class ErrorLogRecord(
        val errorCode: Int,
        val message: String? = null,
        val errors: Map<String, ArrayList<ProjectError>> = emptyMap()
) : LogRecord() {

    companion object {
        const val ERROR_MESSAGE = 0
        const val ERROR_PROJECT = 1
    }
}