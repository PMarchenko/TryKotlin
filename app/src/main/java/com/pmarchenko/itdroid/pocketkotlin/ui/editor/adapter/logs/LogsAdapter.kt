package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.RunLogViewHolder
import com.pmarchenko.itdroid.pocketkotlin.model.log.*

/**
 * @author Pavel Marchenko
 */
class LogsAdapter(context: Context, private val callback: EditorCallback) : RecyclerView.Adapter<LogViewHolder<out LogRecord>>() {

    companion object {
        const val VIEW_TYPE_LOG_RUN = 0
        const val VIEW_TYPE_LOG_INFO = 1
        const val VIEW_TYPE_LOG_ERROR = 2
        const val VIEW_TYPE_EXCEPTION = 3
    }

    private val inflater = LayoutInflater.from(context)
    private val logs = mutableListOf<LogRecord>()

    override fun getItemCount() = logs.size

    private fun getLog(position: Int) = logs[position]

    override fun getItemViewType(position: Int) = when (getLog(position)) {
        is RunLogRecord -> VIEW_TYPE_LOG_RUN
        is InfoLogRecord -> VIEW_TYPE_LOG_INFO
        is ErrorLogRecord -> VIEW_TYPE_LOG_ERROR
        is ExceptionLogRecord -> VIEW_TYPE_EXCEPTION
        else -> error("Unsupported log type: ${logs[position]}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogViewHolder<out LogRecord> = when (viewType) {
        VIEW_TYPE_LOG_RUN -> RunLogViewHolder(
            inflater.inflate(R.layout.content_log, parent, false),
            callback
        )
        VIEW_TYPE_LOG_INFO -> InfoLogViewHolder(
            inflater.inflate(R.layout.content_log, parent, false),
            callback
        )
        VIEW_TYPE_LOG_ERROR -> ErrorLogViewHolder(
            inflater.inflate(R.layout.content_log, parent, false),
            callback
        )
        VIEW_TYPE_EXCEPTION -> ExceptionLogViewHolder(
            inflater.inflate(R.layout.content_log, parent, false),
            callback
        )
        else -> error("Unsupported viewType: $viewType")
    }

    override fun onBindViewHolder(holder: LogViewHolder<out LogRecord>, position: Int) {
        holder.bindView(getLog(position))
    }

    fun setLogs(logs: List<LogRecord>) {
        val oldLogs = ArrayList(this.logs)
        this.logs.clear()
        this.logs.addAll(logs)
        val result = DiffUtil.calculateDiff(DiffCallback(oldLogs, logs))
        result.dispatchUpdatesTo(this)
    }

    private class DiffCallback(val oldList: ArrayList<LogRecord>, val newList: List<LogRecord>) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].timestampNano == newList[newItemPosition].timestampNano

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}