package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.model.log.LogRecord
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs.LogsAdapter
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs.LogsContentData
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.HolderDelegate

/**
 * @author Pavel Marchenko
 */
class HolderDelegateLogs(private val callback: EditorCallback) : HolderDelegate<HolderDelegateLogs.LogsViewHolder, LogsContentData>() {

    override fun create(inflater: LayoutInflater, parent: ViewGroup): LogsViewHolder {
        return LogsViewHolder(inflater.inflate(R.layout.viewholder_logs, parent, false), callback)
    }

    override fun bind(holder: LogsViewHolder, position: Int, contentData: LogsContentData) {
        holder.bindView(contentData.logs)
    }

    class LogsViewHolder(itemView: View, callback: EditorCallback) : RecyclerView.ViewHolder(itemView) {

        private val adapter: LogsAdapter = LogsAdapter(callback)
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
}