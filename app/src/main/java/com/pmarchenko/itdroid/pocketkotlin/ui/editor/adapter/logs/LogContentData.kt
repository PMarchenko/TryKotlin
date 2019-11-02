package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import com.pmarchenko.itdroid.pocketkotlin.model.log.LogRecord
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentData

/**
 * @author Pavel Marchenko
 */
class LogContentData(
    override val viewType: Int,
    val log: LogRecord
) : ContentData {

    override val itemId = 0L

    override fun isItemTheSame(data: ContentData): Boolean {
        return if (data is LogContentData) {
            log == data.log
        } else false
    }

    override fun isContentTheSame(data: ContentData): Boolean {
        return equals(data)
    }
}