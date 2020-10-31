package com.itdroid.pocketkotlin.projects.model

/**
 * @author itdroid
 */
data class TestResult internal constructor(
    val output: String,
    val className: String,
    val methodName: String,
    val executionTime: Long,
    val exception: ProjectException?,
    val status: TestStatus,
    val sourceFileName: String?,
    val failure: ProjectException?,
)

internal fun ApiTestResult.fromApiTestResult(): TestResult =
    TestResult(
        output = output ?: "",
        className = className ?: "",
        methodName = methodName ?: "",
        executionTime = executionTime ?: 0L,
        exception = exception?.fromApiException(),
        status = status?.fromApiTestStatus() ?: TestStatus.Fail,
        sourceFileName = sourceFileName,
        failure = failure?.fromApiException()
    )