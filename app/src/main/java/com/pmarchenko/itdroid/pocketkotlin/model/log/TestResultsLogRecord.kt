package com.pmarchenko.itdroid.pocketkotlin.model.log

import com.pmarchenko.itdroid.pocketkotlin.model.project.TestResult

/**
 * @author Pavel Marchenko
 */
data class TestResultsLogRecord(
    val results: Map<String, ArrayList<TestResult>> = emptyMap()
) : LogRecord()