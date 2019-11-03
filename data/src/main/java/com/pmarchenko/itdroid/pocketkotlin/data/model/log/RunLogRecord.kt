package com.pmarchenko.itdroid.pocketkotlin.data.model.log

/**
 * @author Pavel Marchenko
 */
data class RunLogRecord(val projectName: String, val args: String) : LogRecord()