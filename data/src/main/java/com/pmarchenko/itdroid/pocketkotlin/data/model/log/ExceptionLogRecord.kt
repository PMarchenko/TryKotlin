package com.pmarchenko.itdroid.pocketkotlin.data.model.log

import com.pmarchenko.itdroid.pocketkotlin.data.model.project.ProjectException

/**
 * @author Pavel Marchenko
 */
data class ExceptionLogRecord(val exception: ProjectException) : LogRecord()