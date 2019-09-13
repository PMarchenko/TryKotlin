package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter

import android.text.SpannableStringBuilder
import android.view.View
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs.LogViewHolder
import com.pmarchenko.itdroid.pocketkotlin.model.log.RunLogRecord

/**
 * @author Pavel Marchenko
 */
class RunLogViewHolder(itemView: View, callback: EditorCallback) :
        LogViewHolder<RunLogRecord>(itemView, callback) {

    override fun prepareText(log: RunLogRecord): CharSequence {
        val text = SpannableStringBuilder(super.prepareText(log))
        if (log.args.isEmpty()) {
            text.append(resources.getString(R.string.logs_template_run, log.projectName))
        } else {
            text.append(resources.getString(R.string.logs_template_run_with_args, log.projectName, log.args))
        }
        return text
    }
}