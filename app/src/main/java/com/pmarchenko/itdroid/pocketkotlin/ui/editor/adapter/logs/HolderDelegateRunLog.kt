package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.model.log.RunLogRecord
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback

/**
 * @author Pavel Marchenko
 */
class HolderDelegateRunLog(
    viewType: Int,
    private val callback: EditorCallback
) : HolderDelegateLog<RunLogRecord, HolderDelegateRunLog.RunLogViewHolder>(viewType) {

    override fun create(inflater: LayoutInflater, parent: ViewGroup): RunLogViewHolder {
        return RunLogViewHolder(inflater.inflate(R.layout.viewholder_log, parent, false), callback)
    }

    class RunLogViewHolder(itemView: View, callback: EditorCallback) : LogViewHolder<RunLogRecord>(itemView, callback) {

        override fun prepareText(log: RunLogRecord): CharSequence {
            val text = SpannableStringBuilder(super.prepareText(log))
            if (log.args.isEmpty()) {
                text.append(resources.getString(R.string.logs__template_run, log.projectName))
            } else {
                text.append(resources.getString(R.string.logs__template_run_with_args, log.projectName, log.args))
            }
            return text
        }
    }
}