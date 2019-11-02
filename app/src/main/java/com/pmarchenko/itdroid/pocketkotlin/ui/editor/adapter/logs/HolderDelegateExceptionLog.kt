package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.model.log.ExceptionLogRecord
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectException
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectExceptionStackTrace
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import com.pmarchenko.itdroid.pocketkotlin.utils.ClickableSpanListener

/**
 * @author Pavel Marchenko
 */
class HolderDelegateExceptionLog(private val callback: EditorCallback) :
    HolderDelegateLog<ExceptionLogRecord, HolderDelegateExceptionLog.ExceptionLogViewHolder>() {

    override fun create(inflater: LayoutInflater, parent: ViewGroup): ExceptionLogViewHolder {
        return ExceptionLogViewHolder(inflater.inflate(R.layout.viewholder_log, parent, false), callback)
    }

    class ExceptionLogViewHolder(itemView: View, callback: EditorCallback) :
        LogViewHolder<ExceptionLogRecord>(itemView, callback), ClickableSpanListener<ProjectExceptionStackTrace> {

        override fun prepareText(log: ExceptionLogRecord): CharSequence {
            val text = SpannableStringBuilder(super.prepareText(log))
            appendException(text, log.exception)
            return asError(text)
        }

        private fun appendException(text: SpannableStringBuilder, exception: ProjectException) {
            text.append(resources.getString(R.string.logs__template__exception, exception.fullName, exception.message))

            exception.stackTrace.fold(text) { text, stackTrace ->
                appendStackTrace(text, stackTrace)
                text
            }
            if (exception.cause != null) {
                text.append("\tcaused by:\n")
                appendException(text, exception.cause)
            }
        }

        private fun appendStackTrace(text: SpannableStringBuilder, stackTrace: ProjectExceptionStackTrace) {
            val linkText = "${stackTrace.fileName}${readableLineNumber(stackTrace.lineNumber)}"
            val link = asLink(linkText, stackTrace, this, linkUnderlineTextColor)

            text.append("\n\tat ")
                .append(stackTrace.className).append(".").append(stackTrace.methodName)
                .append(" (").append(link).append(")")
        }

        override fun onClick(data: ProjectExceptionStackTrace, view: View) {
            callback.openFile(data.fileName, data.lineNumber - 1, 0)
        }

        private fun readableLineNumber(lineNumber: Int) = if (lineNumber < 0) "" else ":$lineNumber"
    }
}