package com.pmarchenko.itdroid.pocketkotlin.model.log

import com.pmarchenko.itdroid.pocketkotlin.model.ProjectException

/**
 * @author Pavel Marchenko
 */
data class ExceptionLogRecord(
    val exception: ProjectException
) : LogRecord()