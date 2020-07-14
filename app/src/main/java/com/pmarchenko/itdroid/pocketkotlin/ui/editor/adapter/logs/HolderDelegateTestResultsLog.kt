package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.color
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectException
import com.pmarchenko.itdroid.pocketkotlin.projects.model.TestStatus
import com.pmarchenko.itdroid.pocketkotlin.ui.ClickableSpanListener
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback

/**
 * @author Pavel Marchenko
 */
class HolderDelegateTestResultsLog(private val callback: EditorCallback) :
    HolderDelegateLog<TestResultsLogRecord, TestResultsViewHolder>() {

    override fun create(inflater: LayoutInflater, parent: ViewGroup): TestResultsViewHolder {
        return TestResultsViewHolder(
            inflater.inflate(R.layout.viewholder_log, parent, false),
            callback
        )
    }
}

class TestResultsViewHolder(itemView: View, callback: EditorCallback) :
    LogViewHolder<TestResultsLogRecord>(itemView, callback),
    ClickableSpanListener<ProjectException> {

    //todo color to resources
    private val passedTestTextColor = Color.parseColor("#BB499C54")

    override fun onClick(data: ProjectException, view: View) {
        callback.showExceptionDetails(data)
    }

    override fun describeLog(log: TestResultsLogRecord): CharSequence {
        val out = SpannableStringBuilder()

        var allTests = 0
        var failedTests = 0
        var executionTimeSeconds = 0f

        return log.results
            .flatMap { (_, results) -> results }
            .onEach { result ->
                executionTimeSeconds += result.executionTime / 1000f
                allTests++
                if (result.status == TestStatus.FAIL) failedTests++
            }
            .also { describeTestRun(out, allTests, failedTests, executionTimeSeconds) }
            .fold(out) { acc, result ->
                acc.apply {
                    append("\n${result.methodName}: ")
                    color(result.status.color) {
                        append(
                            result.failure?.toLink(result.status.name)
                                ?: result.status.name
                        )
                    }
                }
            }
    }

    private fun describeTestRun(
        out: SpannableStringBuilder,
        allTests: Int,
        failedTests: Int,
        time: Float
    ) {
        out.append("\n")
        if (failedTests == 0) {
            out.color(passedTestTextColor) {
                append(getString(R.string.logs__test_results__status_passed, time))
            }
        } else {
            out.color(errorTextColor) {
                append(
                    getString(
                        R.string.logs__test_results__status_failed,
                        failedTests,
                        allTests,
                        time
                    )
                )
            }
        }
    }

    private val TestStatus.color: Int
        get() = when (this) {
            TestStatus.OK -> passedTestTextColor
            TestStatus.FAIL -> errorTextColor
        }

    private fun ProjectException.toLink(text: CharSequence): CharSequence {
        return link(text, this, this@TestResultsViewHolder, linkUnderlineTextColor)
    }
}