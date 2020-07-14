package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */
enum class ErrorSeverity { ERROR, WARNING }

internal fun ApiErrorSeverity.fromApiErrorSeverity(): ErrorSeverity =
    when (this) {
        ApiErrorSeverity.ERROR -> ErrorSeverity.ERROR
        ApiErrorSeverity.WARNING -> ErrorSeverity.WARNING
    }