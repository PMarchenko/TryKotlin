package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */
data class ProjectError internal constructor(
    val message: String,
    val severity: ErrorSeverity,
    val interval: Interval,
)

internal fun ApiProjectError.fromProjectError(): ProjectError =
    ProjectError(
        message = message ?: "",
        severity = severity
            ?.fromApiErrorSeverity()
            ?: ErrorSeverity.Error,
        interval = interval
            ?.fromApiInterval()
            ?: Interval(
                start = Position(0, 0),
                end = Position(0, 0)
            )
    )