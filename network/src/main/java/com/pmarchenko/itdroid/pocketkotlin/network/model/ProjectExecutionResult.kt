package com.pmarchenko.itdroid.pocketkotlin.network.model

import com.google.gson.annotations.SerializedName

/**
 * @author Pavel Marchenko
 */
data class ProjectExecutionResult(
    val text: String?,
    val exception: ProjectException?,
    val errors: MutableMap<String, MutableList<ProjectError>>,
    val testResults: MutableMap<String, MutableList<TestResult>>?
)

data class ProjectException(
    val message: String,
    val fullName: String,
    val stackTrace: List<ProjectExceptionStackTrace>,
    val cause: ProjectException?
)

data class ProjectExceptionStackTrace(
    val className: String,
    val methodName: String,
    val fileName: String,
    val lineNumber: Int
)

data class ProjectError(
    val message: String,
    val severity: ErrorSeverity,
    @SerializedName("className") val type: String,
    val interval: Interval
)

data class Interval(val start: Position, val end: Position)

data class Position(val line: Int, val ch: Int)

@Suppress("unused")
/**
 * Used for GSON serialization/deserialization
 * */
enum class ErrorSeverity { ERROR, WARNING }

data class TestResult(
    val output: String,
    val className: String,
    val methodName: String,
    val executionTime: Long,
    val exception: ProjectException?,
    val status: TestStatus,
    val sourceFileName: String,
    @SerializedName("comparisonFailure")
    val failure: ProjectException?
)

@Suppress("unused")
/**
 * Used for GSON serialization/deserialization
 * */
enum class TestStatus { OK, FAIL }