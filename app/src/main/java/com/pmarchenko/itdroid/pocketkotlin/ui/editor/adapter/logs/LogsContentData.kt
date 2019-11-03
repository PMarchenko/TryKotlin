package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import com.pmarchenko.itdroid.pocketkotlin.data.model.log.LogRecord
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentData

/**
 * @author Pavel Marchenko
 */
class LogsContentData(override val viewType: Int, val logs: List<LogRecord>) : ContentData {

    override val itemId = 0L

    override fun isItemTheSame(data: ContentData): Boolean {
        return viewType == data.viewType
    }

    override fun isContentTheSame(data: ContentData): Boolean {
        return equals(data)
    }
}