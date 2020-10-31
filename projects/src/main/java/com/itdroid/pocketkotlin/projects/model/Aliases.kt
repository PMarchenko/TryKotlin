package com.itdroid.pocketkotlin.projects.model

/**
 * @author itdroid
 */

//database module
internal typealias DbProject = com.itdroid.pocketkotlin.database.entity.Project
internal typealias DbProjectFile = com.itdroid.pocketkotlin.database.entity.ProjectFile
internal typealias DbProjectType = com.itdroid.pocketkotlin.database.entity.ProjectType
internal typealias DbProjectStatus = com.itdroid.pocketkotlin.database.entity.ProjectStatus
internal typealias DbExample = com.itdroid.pocketkotlin.database.entity.Example

//network module
internal typealias ApiProject = com.itdroid.pocketkotlin.network.model.Project
internal typealias ApiProjectExecutionResult = com.itdroid.pocketkotlin.network.model.ProjectExecutionResult
internal typealias ApiProjectException = com.itdroid.pocketkotlin.network.model.ProjectException
internal typealias ApiProjectExceptionStackTrace = com.itdroid.pocketkotlin.network.model.ProjectExceptionStackTrace
internal typealias ApiProjectError = com.itdroid.pocketkotlin.network.model.ProjectError
internal typealias ApiInterval = com.itdroid.pocketkotlin.network.model.Interval
internal typealias ApiPosition = com.itdroid.pocketkotlin.network.model.Position
internal typealias ApiErrorSeverity = com.itdroid.pocketkotlin.network.model.ErrorSeverity
internal typealias ApiTestResult = com.itdroid.pocketkotlin.network.model.TestResult
internal typealias ApiTestStatus = com.itdroid.pocketkotlin.network.model.TestStatus