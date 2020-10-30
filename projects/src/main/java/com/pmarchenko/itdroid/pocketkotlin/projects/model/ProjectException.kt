package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */
data class ProjectException internal constructor(
    val message: String,
    val fullName: String,
    val stackTrace: List<ProjectExceptionStackTrace>,
    val cause: ProjectException?,
)

internal fun ApiProjectException.fromApiException(): ProjectException =
    ProjectException(
        message = message ?: "",
        fullName = fullName ?: "",
        stackTrace = stackTrace
            ?.map(ApiProjectExceptionStackTrace::fromApiStackTrace)
            ?: emptyList(),
        cause = cause?.fromApiException()
    )