package com.itdroid.pocketkotlin.projects.model

/**
 * @author itdroid
 */
data class ProjectExceptionStackTrace internal constructor(
    val className: String?,
    val methodName: String?,
    val fileName: String?,
    val lineNumber: Int?
)

internal fun ApiProjectExceptionStackTrace.fromApiStackTrace(): ProjectExceptionStackTrace =
    ProjectExceptionStackTrace(
        className = className,
        methodName = methodName,
        fileName = fileName,
        lineNumber = lineNumber
    )