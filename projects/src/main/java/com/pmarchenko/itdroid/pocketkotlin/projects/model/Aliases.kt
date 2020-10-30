package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */

//database module
internal typealias DbProject = com.pmarchenko.itdroid.pocketkotlin.database.entity.Project
internal typealias DbProjectFile = com.pmarchenko.itdroid.pocketkotlin.database.entity.ProjectFile
internal typealias DbProjectType = com.pmarchenko.itdroid.pocketkotlin.database.entity.ProjectType
internal typealias DbProjectStatus = com.pmarchenko.itdroid.pocketkotlin.database.entity.ProjectStatus
internal typealias DbExample = com.pmarchenko.itdroid.pocketkotlin.database.entity.Example

//network module
internal typealias ApiProject = com.pmarchenko.itdroid.pocketkotlin.network.model.Project
internal typealias ApiProjectExecutionResult = com.pmarchenko.itdroid.pocketkotlin.network.model.ProjectExecutionResult
internal typealias ApiProjectException = com.pmarchenko.itdroid.pocketkotlin.network.model.ProjectException
internal typealias ApiProjectExceptionStackTrace = com.pmarchenko.itdroid.pocketkotlin.network.model.ProjectExceptionStackTrace
internal typealias ApiProjectError = com.pmarchenko.itdroid.pocketkotlin.network.model.ProjectError
internal typealias ApiInterval = com.pmarchenko.itdroid.pocketkotlin.network.model.Interval
internal typealias ApiPosition = com.pmarchenko.itdroid.pocketkotlin.network.model.Position
internal typealias ApiErrorSeverity = com.pmarchenko.itdroid.pocketkotlin.network.model.ErrorSeverity
internal typealias ApiTestResult = com.pmarchenko.itdroid.pocketkotlin.network.model.TestResult
internal typealias ApiTestStatus = com.pmarchenko.itdroid.pocketkotlin.network.model.TestStatus