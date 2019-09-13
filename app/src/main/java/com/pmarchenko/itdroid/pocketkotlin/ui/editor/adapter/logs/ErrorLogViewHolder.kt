package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.view.View
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectError
import com.pmarchenko.itdroid.pocketkotlin.model.log.ErrorLogRecord
import com.pmarchenko.itdroid.pocketkotlin.utils.ClickableSpanListener

/**
 * @author Pavel Marchenko
 */
class ErrorLogViewHolder(itemView: View, callback: EditorCallback) :
        LogViewHolder<ErrorLogRecord>(itemView, callback),
        ClickableSpanListener<Pair<String, ProjectError>> {

    override fun prepareText(log: ErrorLogRecord): CharSequence {
        val message = when (log.errorCode) {
            ErrorLogRecord.ERROR_MESSAGE -> log.message
            ErrorLogRecord.ERROR_PROJECT -> {
                val combinedErrors = SpannableStringBuilder("\n")
                log.errors.forEach { errors ->
                    val fileName = errors.key
                    errors.value.forEach { error ->
                        val linkText = "$fileName:${error.interval.start.line + 1}:${error.interval.start.ch + 1}"
                        val link = asLink(linkText, Pair(fileName, error), this, Color.parseColor("#2196F3"))
                        combinedErrors.append(error.severity.name).append(" (").append(link).append("): ${error.message}\n")
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
        callback.openFile(data.first, data.second.interval.start.line, data.second.interval.start.ch)
    }
}