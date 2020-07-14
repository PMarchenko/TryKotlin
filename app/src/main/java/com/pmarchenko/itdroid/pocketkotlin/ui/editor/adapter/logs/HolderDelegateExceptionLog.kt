package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectException
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectExceptionStackTrace
import com.pmarchenko.itdroid.pocketkotlin.ui.ClickableSpanListener
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback

/**
 * @author Pavel Marchenko
 */
class HolderDelegateExceptionLog(private val callback: EditorCallback) :
    HolderDelegateLog<ExceptionLogRecord, ExceptionLogViewHolder>() {

    override fun create(inflater: LayoutInflater, parent: ViewGroup): ExceptionLogViewHolder {
        return ExceptionLogViewHolder(
            inflater.inflate(R.layout.viewholder_log, parent, false),
            callback
        )
    }
}

class ExceptionLogViewHolder(itemView: View, callback: EditorCallback) :
    LogViewHolder<ExceptionLogRecord>(itemView, callback),
    ClickableSpanListener<ProjectExceptionStackTrace> {

    override fun onClick(data: ProjectExceptionStackTrace, view: View) {
        callback.openFile(data.fileName, data.lineNumber - 1, 0)
    }

    override fun prepareText(log: ExceptionLogRecord): CharSequence {
        return error(super.prepareText(log))
    }

    override fun describeLog(log: ExceptionLogRecord) = describeException(log.exception)

    private fun describeException(exception: ProjectException): CharSequence {
        val out = SpannableStringBuilder(
            getString(R.string.logs__template__exception, exception.fullName, exception.message)
        )

        exception.stackTrace.fold(out, ::appendStackTrace)

        exception.cause?.let {
            out.append("\tcaused by:\n").append(describeException(it))
        }

        return out
    }

    private fun appendStackTrace(
        text: SpannableStringBuilder,
        stackTrace: ProjectExceptionStackTrace
    ): SpannableStringBuilder {
        val linkText = "${stackTrace.fileName}${readableLineNumber(stackTrace.lineNumber)}"
        val link = link(linkText, stackTrace, this)

        return text.append("\n\tat ")
            .append(stackTrace.className).append(".").append(stackTrace.methodName)
            .append(" (").append(link).append(")")
    }

    private fun readableLineNumber(lineNumber: Int) =
        if (lineNumber < 0) "" else ":$lineNumber"
}