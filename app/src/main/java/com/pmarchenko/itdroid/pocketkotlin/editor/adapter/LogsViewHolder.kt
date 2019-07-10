package com.pmarchenko.itdroid.pocketkotlin.editor.adapter

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.editor.ProjectCallback
import com.pmarchenko.itdroid.pocketkotlin.model.log.LogRecord

/**
 * @author Pavel Marchenko
 */
class LogsViewHolder(itemView: View, callback: ProjectCallback) : RecyclerView.ViewHolder(itemView) {

    private val adapter: LogsAdapter = LogsAdapter(itemView.context, callback)
    private val logsView = itemView as RecyclerView

    init {
        logsView.layoutManager = LinearLayoutManager(itemView.context)
        logsView.adapter = adapter
    }

    fun bindView(logs: List<LogRecord>) {
        val autoScroll = logs.isNotEmpty() && !logsView.canScrollVertically(1)

        adapter.setLogs(logs)
        if (autoScroll) {
            logsView.scrollToPosition(adapter.itemCount - 1)
        }
    }
}