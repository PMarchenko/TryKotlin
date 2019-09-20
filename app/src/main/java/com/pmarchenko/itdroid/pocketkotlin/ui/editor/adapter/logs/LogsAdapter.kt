package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import com.pmarchenko.itdroid.pocketkotlin.model.log.*
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentAdapter
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.HolderDelegateManager

/**
 * @author Pavel Marchenko
 */
class LogsAdapter(callback: EditorCallback) : ContentAdapter(DelegateManager(callback)) {

    fun setLogs(logs: List<LogRecord>) {
        val content = logs.map { logRecord ->
            val viewType = when (logRecord) {
                is RunLogRecord -> VIEW_TYPE_RUN
                is InfoLogRecord -> VIEW_TYPE_INFO
                is ErrorLogRecord -> VIEW_TYPE_ERROR
                is ExceptionLogRecord -> VIEW_TYPE_EXCEPTION
                else -> error("Unsupported log type: $logRecord")
            }
            LogContentData(viewType, logRecord)
        }
        setContent(content)
    }

    companion object {
        const val VIEW_TYPE_RUN = 0
        const val VIEW_TYPE_INFO = 1
        const val VIEW_TYPE_ERROR = 2
        const val VIEW_TYPE_EXCEPTION = 3
    }

    class DelegateManager(callback: EditorCallback) : HolderDelegateManager() {

        init {
            register(HolderDelegateRunLog(VIEW_TYPE_RUN, callback))
            register(HolderDelegateInfoLog(VIEW_TYPE_INFO, callback))
            register(HolderDelegateErrorLog(VIEW_TYPE_ERROR, callback))
            register(HolderDelegateExceptionLog(VIEW_TYPE_EXCEPTION, callback))
        }
    }
}