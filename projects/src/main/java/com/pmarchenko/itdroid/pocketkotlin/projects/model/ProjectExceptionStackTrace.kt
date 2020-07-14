package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */
data class ProjectExceptionStackTrace(
    val className: String,
    val methodName: String,
    val fileName: String,
    val lineNumber: Int
)

internal fun ApiProjectExceptionStackTrace.fromApiStackTrace(): ProjectExceptionStackTrace =
    ProjectExceptionStackTrace(
        className = className,
        methodName = methodName,
        fileName = fileName,
        lineNumber = lineNumber
    )