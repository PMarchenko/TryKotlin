package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentAdapter
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentData
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.HolderDelegate

/**
 * @author Pavel Marchenko
 */
class LogsAdapter(val callback: EditorCallback) : ContentAdapter() {

    override val delegates: Map<Int, HolderDelegate<out RecyclerView.ViewHolder, out ContentData>> =
        mapOf(
            VIEW_TYPE_RUN to HolderDelegateRunLog(callback),
            VIEW_TYPE_INFO to HolderDelegateInfoLog(callback),
            VIEW_TYPE_ERROR to HolderDelegateErrorLog(callback),
            VIEW_TYPE_EXCEPTION to HolderDelegateExceptionLog(callback),
            VIEW_TYPE_TEST_RESULTS to HolderDelegateTestResultsLog(callback)
        )

    fun setLogs(logs: List<LogRecord>) {
        setContent(logs.map(::toContent))
    }

    companion object {

        private const val VIEW_TYPE_RUN = 0
        private const val VIEW_TYPE_INFO = 1
        private const val VIEW_TYPE_ERROR = 2
        private const val VIEW_TYPE_EXCEPTION = 3
        private const val VIEW_TYPE_TEST_RESULTS = 4

        private fun toContent(log: LogRecord): LogContentData {
            val viewType = when (log) {
                is RunLogRecord -> VIEW_TYPE_RUN
                is InfoLogRecord -> VIEW_TYPE_INFO
                is ErrorLogRecord -> VIEW_TYPE_ERROR
                is ExceptionLogRecord -> VIEW_TYPE_EXCEPTION
                is TestResultsLogRecord -> VIEW_TYPE_TEST_RESULTS
            }
            return LogContentData(viewType, log)
        }
    }
}