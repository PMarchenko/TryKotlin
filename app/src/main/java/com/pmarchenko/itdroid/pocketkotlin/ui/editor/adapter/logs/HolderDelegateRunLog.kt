package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback

/**
 * @author Pavel Marchenko
 */
class HolderDelegateRunLog(private val callback: EditorCallback) :
    HolderDelegateLog<RunLogRecord, RunLogViewHolder>() {

    override fun create(inflater: LayoutInflater, parent: ViewGroup): RunLogViewHolder {
        return RunLogViewHolder(
            inflater.inflate(R.layout.viewholder_log, parent, false),
            callback
        )
    }
}

class RunLogViewHolder(itemView: View, callback: EditorCallback) :
    LogViewHolder<RunLogRecord>(itemView, callback) {

    override fun describeLog(log: RunLogRecord): CharSequence {
        return if (log.args.isEmpty()) {
            getString(R.string.logs__template__run, log.projectName)
        } else {
            getString(R.string.logs__template__run_with_args, log.projectName, log.args)
        }
    }
}