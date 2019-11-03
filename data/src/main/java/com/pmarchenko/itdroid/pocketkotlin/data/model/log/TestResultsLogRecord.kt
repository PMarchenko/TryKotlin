package com.pmarchenko.itdroid.pocketkotlin.data.model.log

import com.pmarchenko.itdroid.pocketkotlin.data.model.project.TestResult

/**
 * @author Pavel Marchenko
 */
data class TestResultsLogRecord(val results: Map<String, ArrayList<TestResult>> = emptyMap())
    : LogRecord()