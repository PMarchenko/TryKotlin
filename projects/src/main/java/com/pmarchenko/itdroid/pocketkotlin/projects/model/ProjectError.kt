package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */
data class ProjectError(
    val message: String,
    val severity: ErrorSeverity,
    val interval: Interval
)

internal fun ApiProjectError.fromProjectError(): ProjectError =
    ProjectError(
        message = message,
        severity = severity.fromApiErrorSeverity(),
        interval = interval.fromApiInterval()
    )