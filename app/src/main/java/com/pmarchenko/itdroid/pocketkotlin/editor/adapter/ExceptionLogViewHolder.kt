package com.pmarchenko.itdroid.pocketkotlin.editor.adapter

import android.text.SpannableStringBuilder
import android.view.View
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.model.ProjectException
import com.pmarchenko.itdroid.pocketkotlin.model.log.ExceptionLogRecord

/**
 * @author Pavel Marchenko
 */
class ExceptionLogViewHolder(itemView: View) : LogViewHolder<ExceptionLogRecord>(itemView) {

    override fun prepareText(log: ExceptionLogRecord): CharSequence {
        val text = SpannableStringBuilder(super.prepareText(log))
        appendException(text, log.exception)
        return asError(text)
    }

    private fun appendException(text: SpannableStringBuilder, exception: ProjectException) {
        text.append(resources.getString(R.string.logs_template_exception, exception.fullName, exception.message))
        exception.stackTrace.forEach { stackTrace -> appendStackTrace(text, stackTrace) }
        if (exception.cause != null) {
            text.append("\tcaused by:\n")
            appendException(text, exception.cause)
        }
    }

    private fun appendStackTrace(text: SpannableStringBuilder, stackTrace: ProjectException.ProjectExceptionStackTrace) {
        text.append("\n\t${stackTrace.className}.${stackTrace.methodName} (${stackTrace.fileName}${readableLineNumber(stackTrace.lineNumber)})")
    }

    private fun readableLineNumber(lineNumber: Int) = if (lineNumber < 0) "" else ":$lineNumber"
}