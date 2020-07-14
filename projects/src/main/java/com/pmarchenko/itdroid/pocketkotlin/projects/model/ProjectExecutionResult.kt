package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */
data class ProjectExecutionResult(
    val text: String?,
    val exception: ProjectException?,
    val errors: Map<String, List<ProjectError>>,
    val testResults: Map<String, List<TestResult>>?
)

internal fun ApiProjectExecutionResult.fromApiExecutionResult() = ProjectExecutionResult(
    text = text,
    exception = exception?.fromApiException(),
    errors = errors.mapValues {
        it.value.map { error -> error.fromProjectError() }
    },
    testResults = testResults?.mapValues {
        it.value.map { result -> result.fromApiTestResult() }
    }
)