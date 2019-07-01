package com.pmarchenko.itdroid.trykotlin.model

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
data class ProjectException(
    val message: String,
    val fullName: String,
    val stackTrace: List<ProjectExceptionStackTrace>,
    val cause: ProjectException?
)

data class ProjectExceptionStackTrace(
    val className: String,
    val methodName: String,
    val fileName: String,
    val lineNumber: Int
)
