package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */
data class TestResult(
    val output: String,
    val className: String,
    val methodName: String,
    val executionTime: Long,
    val exception: ProjectException?,
    val status: TestStatus,
    val sourceFileName: String,
    val failure: ProjectException?
)

internal fun ApiTestResult.fromApiTestResult(): TestResult =
    TestResult(
        output = output,
        className = className,
        methodName = methodName,
        executionTime = executionTime,
        exception = exception?.fromApiException(),
        status = status.fromApiTestStatus(),
        sourceFileName = sourceFileName,
        failure = failure?.fromApiException()
    )