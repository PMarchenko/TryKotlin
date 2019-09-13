package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import com.pmarchenko.itdroid.pocketkotlin.model.log.LogRecord
import com.pmarchenko.itdroid.pocketkotlin.recycler.ContentData

/**
 * @author Pavel Marchenko
 */
class LogsContentData(override val viewType: Int, val logs: List<LogRecord>) : ContentData {

    override fun isItemTheSame(data: ContentData) = viewType == data.viewType

    override fun isContentTheSame(data: ContentData): Boolean {
        if (data !is LogsContentData) return false
        return logs == data.logs
    }
}