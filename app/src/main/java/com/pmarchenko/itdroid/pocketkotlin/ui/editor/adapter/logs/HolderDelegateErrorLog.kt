package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectError
import com.pmarchenko.itdroid.pocketkotlin.ui.ClickableSpanListener
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback

/**
 * @author Pavel Marchenko
 */
class HolderDelegateErrorLog(private val callback: EditorCallback) :
    HolderDelegateLog<ErrorLogRecord, ErrorLogViewHolder>() {

    override fun create(inflater: LayoutInflater, parent: ViewGroup): ErrorLogViewHolder {
        return ErrorLogViewHolder(
            inflater.inflate(R.layout.viewholder_log, parent, false),
            callback
        )
    }
}

class ErrorLogViewHolder(itemView: View, callback: EditorCallback) :
    LogViewHolder<ErrorLogRecord>(itemView, callback),
    ClickableSpanListener<Pair<String, ProjectError>> {

    override fun onClick(data: Pair<String, ProjectError>, view: View) {
        callback.openFile(
            data.first,
            data.second.interval.start.line,
            data.second.interval.start.ch
        )
    }

    override fun prepareText(log: ErrorLogRecord): CharSequence {
        return error(super.prepareText(log))
    }

    override fun describeLog(log: ErrorLogRecord): CharSequence {
        return when (log.errorType) {
            ErrorTypeType.ERROR_MESSAGE -> log.message ?: ""
            ErrorTypeType.ERROR_PROJECT -> {
                log.errors
                    .flatMap { (file, errors) -> errors.map { file to it } }
                    .fold(SpannableStringBuilder("\n"), ::describeError)
            }
        }
    }

    private fun describeError(
        out: SpannableStringBuilder,
        errorData: Pair<String, ProjectError>
    ): SpannableStringBuilder {
        val (file, error) = errorData

        val link = link(
            "$file:${error.interval.start.line + 1}:${error.interval.start.ch + 1}",
            errorData,
            this
        )

        return out.append(error.severity.name)
            .append("${error.severity.name} ")
            .append(link) //has to separate as it is CharSequence
            .append(" ${error.message}\n")
    }
}