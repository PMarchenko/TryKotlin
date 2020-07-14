package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectError
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectException
import com.pmarchenko.itdroid.pocketkotlin.projects.model.TestResult

/**
 * @author Pavel Marchenko
 */
sealed class LogRecord(val timestamp: Long = System.currentTimeMillis())

data class RunLogRecord(val projectName: String, val args: String) : LogRecord()

data class InfoLogRecord(val message: String) : LogRecord()

data class TestResultsLogRecord(
    val results: Map<String, List<TestResult>> = emptyMap()
) : LogRecord()

data class ExceptionLogRecord(val exception: ProjectException) : LogRecord()

data class ErrorLogRecord(
    val errorType: ErrorTypeType,
    val message: String? = null,
    val errors: Map<String, List<ProjectError>> = emptyMap()
) : LogRecord()

enum class ErrorTypeType { ERROR_MESSAGE, ERROR_PROJECT }
