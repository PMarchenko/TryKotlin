package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */
enum class ErrorSeverity {

    Error,
    Warning
}

internal fun ApiErrorSeverity.fromApiErrorSeverity(): ErrorSeverity =
    when (this) {
        ApiErrorSeverity.Error -> ErrorSeverity.Error
        ApiErrorSeverity.Warning -> ErrorSeverity.Warning
    }