package com.itdroid.pocketkotlin.editor.logs

import com.itdroid.pocketkotlin.projects.model.ProjectError
import com.itdroid.pocketkotlin.projects.model.ProjectException
import com.itdroid.pocketkotlin.projects.model.TestResult
import java.util.*

/**
 * @author itdroid
 */
sealed class LogRecord(val timestamp: Long = System.currentTimeMillis()) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LogRecord

        return timestamp == other.timestamp
    }

    override fun hashCode() = Objects.hash(super.hashCode(), timestamp)

}

data class ExecuteLogRecord(val projectName: String, val args: String) : LogRecord() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as ExecuteLogRecord

        return projectName == other.projectName &&
                args == other.args
    }

    override fun hashCode() = Objects.hash(super.hashCode(), projectName, args)
}

data class InfoLogRecord(val message: String) : LogRecord() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as InfoLogRecord

        return message == other.message
    }

    override fun hashCode() = Objects.hash(super.hashCode(), message)
}

data class TestResultsLogRecord(val results: Map<String, List<TestResult>>) :
    LogRecord() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as TestResultsLogRecord

        return results == other.results
    }

    override fun hashCode() = Objects.hash(super.hashCode(), results)
}

data class ExceptionLogRecord(val exception: ProjectException) : LogRecord() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as ExceptionLogRecord

        return exception == other.exception
    }

    override fun hashCode() = Objects.hash(super.hashCode(), exception)
}

data class ErrorLogRecord(
    val errorType: ErrorType,
    val message: String? = null,
    val errors: Map<String, List<ProjectError>> = emptyMap(),
) : LogRecord() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as ErrorLogRecord

        return errorType == other.errorType &&
                message == other.message &&
                errors == other.errors
    }

    override fun hashCode() = Objects.hash(super.hashCode(), errorType, message, errors)
}

enum class ErrorType {

    Message,
    Project
}
