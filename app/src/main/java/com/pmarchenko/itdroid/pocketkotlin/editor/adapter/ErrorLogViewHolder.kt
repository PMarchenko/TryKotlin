package com.pmarchenko.itdroid.pocketkotlin.editor.adapter

import android.text.SpannableStringBuilder
import android.view.View
import com.pmarchenko.itdroid.pocketkotlin.model.log.ErrorLogRecord

/**
 * @author Pavel Marchenko
 */
class ErrorLogViewHolder(itemView: View) : LogViewHolder<ErrorLogRecord>(itemView) {

    override fun prepareText(log: ErrorLogRecord): CharSequence {
        val message = when (log.errorCode) {
            ErrorLogRecord.ERROR_MESSAGE -> log.message
            ErrorLogRecord.ERROR_PROJECT -> {
                val combinedErrors = StringBuilder("\n")
                log.errors.forEach { errors ->
                    val fileName = errors.key
                    errors.value.forEach { error ->
                        combinedErrors.append("[${error.severity}] $fileName (${error.interval.start.line + 1}, ${error.interval.start.ch + 1}): ${error.message}\n")
                    }
                }
                combinedErrors
            }
            else -> throw IllegalStateException("Unsupported error type ${log.errorCode}")
        } as CharSequence

        val out = SpannableStringBuilder(super.prepareText(log)).append(message)

        return asError(out)
    }
}