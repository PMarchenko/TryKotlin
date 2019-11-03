package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import com.pmarchenko.itdroid.pocketkotlin.data.model.log.*
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentAdapter
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.HolderDelegate

/**
 * @author Pavel Marchenko
 */
class LogsAdapter(private val callback: EditorCallback) : ContentAdapter() {

    fun setLogs(logs: List<LogRecord>) {
        val content = logs.map { logRecord -> LogContentData(asViewType(logRecord), logRecord) }
        setContent(content)
    }

    override fun delegates(): Map<Int, HolderDelegate<*, *>> =
        mapOf(
            VIEW_TYPE_RUN to HolderDelegateRunLog(callback),
            VIEW_TYPE_INFO to HolderDelegateInfoLog(callback),
            VIEW_TYPE_ERROR to HolderDelegateErrorLog(callback),
            VIEW_TYPE_EXCEPTION to HolderDelegateExceptionLog(callback),
            VIEW_TYPE_TEST_RESULTS to HolderDelegateTestResultsLog(callback)
        )

    companion object {

        const val VIEW_TYPE_RUN = 0
        const val VIEW_TYPE_INFO = 1
        const val VIEW_TYPE_ERROR = 2
        const val VIEW_TYPE_EXCEPTION = 3
        const val VIEW_TYPE_TEST_RESULTS = 4

        private fun asViewType(logRecord: LogRecord) =
            when (logRecord) {
                is RunLogRecord -> VIEW_TYPE_RUN
                is InfoLogRecord -> VIEW_TYPE_INFO
                is ErrorLogRecord -> VIEW_TYPE_ERROR
                is ExceptionLogRecord -> VIEW_TYPE_EXCEPTION
                is TestResultsLogRecord -> VIEW_TYPE_TEST_RESULTS
                else -> error("Unsupported log type: ${logRecord::class.java.canonicalName}")
            }
    }
}