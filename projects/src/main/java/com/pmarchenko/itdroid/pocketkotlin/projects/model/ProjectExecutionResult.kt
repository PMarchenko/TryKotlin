package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */
data class ProjectExecutionResult internal constructor(
    val text: String?,
    val exception: ProjectException?,
    val errors: Map<String, List<ProjectError>>,
    val testResults: Map<String, List<TestResult>>,
)

internal fun ApiProjectExecutionResult.fromApiExecutionResult() = ProjectExecutionResult(
    text = text,
    exception = exception?.fromApiException(),
    errors = errors
        ?.mapValues {
            it.value.map { error -> error.fromProjectError() }
        }
        ?: emptyMap(),

    testResults = testResults
        ?.mapValues {
            it.value.map { result -> result.fromApiTestResult() }
        }
        ?: emptyMap()
)