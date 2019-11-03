package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.data.model.log.ErrorLogRecord
import com.pmarchenko.itdroid.pocketkotlin.data.model.project.ProjectError
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import com.pmarchenko.itdroid.pocketkotlin.domain.utils.ClickableSpanListener

/**
 * @author Pavel Marchenko
 */
class HolderDelegateErrorLog(private val callback: EditorCallback) :
    HolderDelegateLog<ErrorLogRecord, HolderDelegateErrorLog.ErrorLogViewHolder>() {

    override fun create(inflater: LayoutInflater, parent: ViewGroup): ErrorLogViewHolder {
        return ErrorLogViewHolder(
            inflater.inflate(R.layout.viewholder_log, parent, false),
            callback
        )
    }

    class ErrorLogViewHolder(itemView: View, callback: EditorCallback) :
        LogViewHolder<ErrorLogRecord>(itemView, callback),
        ClickableSpanListener<Pair<String, ProjectError>> {

        override fun prepareText(log: ErrorLogRecord): CharSequence {
            val message = when (log.errorCode) {
                ErrorLogRecord.ERROR_MESSAGE -> log.message
                ErrorLogRecord.ERROR_PROJECT -> {
                    val combinedErrors = SpannableStringBuilder("\n")
                    for (fileErrors in log.errors) {
                        val fileName = fileErrors.key
                        for (error in fileErrors.value) {
                            val linkText =
                                "$fileName:${error.interval.start.line + 1}:${error.interval.start.ch + 1}"
                            val link = asLink(
                                linkText,
                                Pair(fileName, error),
                                this,
                                linkUnderlineTextColor
                            )
                            combinedErrors.append("${error.severity.name} ($link): ${error.message}\n")
                        }
                    }

                    combinedErrors
                }
                else -> error("Unsupported error type ${log.errorCode}")
            } as CharSequence

            val out = SpannableStringBuilder(super.prepareText(log)).append(message)

            return asError(out)
        }

        override fun onClick(data: Pair<String, ProjectError>, view: View) {
            callback.openFile(
                data.first,
                data.second.interval.start.line,
                data.second.interval.start.ch
            )
        }
    }
}