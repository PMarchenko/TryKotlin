package com.itdroid.pocketkotlin.editor.logs

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import androidx.core.text.set
import androidx.core.text.toSpannable
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.projects.model.ErrorSeverity
import com.itdroid.pocketkotlin.utils.checkAllMatched
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author itdroid
 */
class SpannableLogsProcessor(
    private val context: Context,
    private val dateFormat: SimpleDateFormat =
        SimpleDateFormat(context.getString(R.string.logs__timestamp_format), Locale.getDefault()),
) : AppLogsProcessor<Spannable> {

    override fun process(log: LogRecord) =
        SpannableStringBuilder()
            .apply {
                timestamp(log)
                when (log) {
                    is ExecuteLogRecord -> process(log)
                    is InfoLogRecord -> process(log)
                    is TestResultsLogRecord -> process(log)
                    is ExceptionLogRecord -> process(log)
                    is ErrorLogRecord -> process(log)
                }.checkAllMatched
            }
            .toSpannable()

    private fun SpannableStringBuilder.timestamp(log: LogRecord) {
        regularText("${dateFormat.format(log.timestamp)}: ")
    }

    private fun SpannableStringBuilder.process(log: ExecuteLogRecord) {
        regularText(
            if (log.args.isEmpty()) {
                context.getString(R.string.logs__execute_project__no_args, log.projectName)
            } else {
                context.getString(R.string.logs__execute_project, log.projectName, log.args)
            }
        )
    }

    private fun SpannableStringBuilder.process(log: InfoLogRecord) {
        var text = log.message
            .replace("<outStream>", "")
            .replace("</outStream>", "")
            .trim()

        var errorStart = text.indexOf("<errStream>")
        if (errorStart < 0) {
            regularText(text)
        } else {
            while (errorStart >= 0) {
                regularText(text.substring(0, errorStart))

                text = text.replaceFirst("<errStream>", "")

                val errorEnd = text.indexOf("</errStream>")
                text = text.replaceFirst("</errStream>", "")

                if (errorEnd > errorStart) {
                    errorText(text.substring(errorStart, errorEnd))
                }

                errorStart = text.indexOf("<errStream>")
                if (errorStart < 0) {
                    regularText(text.substring(errorEnd))
                }
            }
        }
    }

    private fun SpannableStringBuilder.process(log: TestResultsLogRecord) {
        val start = length
        log.results
            .flatMap { (_, results) -> results }
            .onEach { result ->
                "\n${result.methodName}: ${result.status.name}".also { msg ->
                    if (result.status.isOk()) {
                        successText(msg)
                    } else {
                        errorText(msg)
                    }
                }
            }
            .fold(TestStats()) { stats, result ->
                stats.apply {
                    testsCount++
                    if (!result.status.isOk()) failedTestsCount++
                    executionTime += result.executionTime / 1000f
                }
            }
            .also { stats ->
                // inserted before test results!!
                val testResults = context.resources.getQuantityString(
                    R.plurals.logs__tests_results,
                    stats.failedTestsCount,
                    stats.failedTestsCount,
                    stats.testsCount,
                    stats.executionTime
                )
                if (stats.failedTestsCount == 0) {
                    regularText(testResults, start)
                } else {
                    errorText(testResults, start)
                }
            }
    }

    private fun SpannableStringBuilder.process(log: ExceptionLogRecord) {
        val exception = log.exception
        errorText(context.getString(R.string.logs__exception,
            exception.fullName,
            exception.message))

        exception.stackTrace.forEach { frame ->
            errorText(context.getString(R.string.logs__exception__at))
            errorText(
                //todo link
                "\n${frame.className}.${frame.methodName} (${frame.fileName}:${frame.lineNumber})"
            )
        }

        exception.cause?.let {
            errorText(context.getString(R.string.logs__exception__caused_by))
            process(ExceptionLogRecord(it))
        }
    }

    private fun SpannableStringBuilder.process(log: ErrorLogRecord) {
        when (log.errorType) {
            ErrorType.Message -> log.message?.let { errorText(it) }
            ErrorType.Project -> {
                log.errors
                    .flatMap { (file, errors) -> errors.map { file to it } }
                    .forEach { (file, error) ->
                        val printer = when (error.severity) {
                            ErrorSeverity.Error -> ::errorText
                            ErrorSeverity.Warning -> ::warningText
                        }.checkAllMatched
                        printer(error.message, length)
                        printer(
                            " - $file:${error.interval.start.line + 1}:${error.interval.start.ch + 1}\n",
                            length
                        )
                    }
                    .also {
                        delete(length - 1, length) //delete las \n }
                    }
            }
        }.checkAllMatched
    }
}

private fun SpannableStringBuilder.regularText(text: String, where: Int = length) {
    insert(where, text)
}

private fun SpannableStringBuilder.errorText(text: String, where: Int = length) {
    textWithSpan(text, where, ErrorSpan())
}

private fun SpannableStringBuilder.warningText(text: String, where: Int = length) {
    textWithSpan(text, where, WarningSpan())
}

private fun SpannableStringBuilder.successText(text: String, where: Int = length) {
    textWithSpan(text, where, SuccessSpan())
}

private fun SpannableStringBuilder.textWithSpan(
    text: String,
    where: Int = length,
    span: Span,
) {
    insert(where, text)
    set(where, where + text.length, span)
}

private class TestStats(
    var testsCount: Int = 0,
    var failedTestsCount: Int = 0,
    var executionTime: Float = 0f,
)