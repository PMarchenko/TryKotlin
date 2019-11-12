package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.logs

import android.graphics.Color
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.core.utils.ClickableSpanListener
import com.pmarchenko.itdroid.pocketkotlin.data.model.log.TestResultsLogRecord
import com.pmarchenko.itdroid.pocketkotlin.data.model.project.ProjectException
import com.pmarchenko.itdroid.pocketkotlin.data.model.project.Status
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorCallback

/**
 * @author Pavel Marchenko
 */
class HolderDelegateTestResultsLog(private val callback: EditorCallback) :
    HolderDelegateLog<TestResultsLogRecord, HolderDelegateTestResultsLog.TestResultsViewHolder>() {

    override fun create(inflater: LayoutInflater, parent: ViewGroup): TestResultsViewHolder {
        return TestResultsViewHolder(
            inflater.inflate(R.layout.viewholder_log, parent, false),
            callback
        )
    }

    class TestResultsViewHolder(itemView: View, callback: EditorCallback) :
        LogViewHolder<TestResultsLogRecord>(itemView, callback),
        ClickableSpanListener<ProjectException> {

        //todo color to resources
        private val passedTestTextColor = Color.parseColor("#BB499C54")

        override fun prepareText(log: TestResultsLogRecord): CharSequence {
            val out = SpannableStringBuilder(super.prepareText(log))

            for (fileName in log.results.keys) {
                val results = log.results[fileName]

                val testLabelEndPosition = out.length
                var failedTests = 0
                var executionTimeSeconds = 0f

                results?.let {
                    for (result in it) {
                        executionTimeSeconds += result.executionTime / 1000f
                        out.append("\n- ${result.methodName}: ")
                        when (val status = result.status) {
                            Status.OK -> out.append(
                                asColoredText(status.name, passedTestTextColor)
                            )
                            Status.FAIL -> {
                                failedTests++
                                val failure = result.failure
                                if (failure == null) {
                                    out.append(asError(status.name))
                                } else {
                                    out.append(
                                        asError(
                                            asLink(
                                                status.name,
                                                failure,
                                                this,
                                                linkUnderlineTextColor
                                            )
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                val status = if (failedTests == 0) {
                    resources.getString(
                        R.string.logs__test_results__status_passed,
                        executionTimeSeconds
                    )
                } else {
                    resources.getString(
                        R.string.logs__test_results__status_failed,
                        failedTests,
                        results?.size ?: failedTests,
                        executionTimeSeconds
                    )
                }
                out.insert(testLabelEndPosition, "\n$status:")
            }
            return out
        }

        override fun onClick(data: ProjectException, view: View) {
            callback.showExceptionDetails(data)
        }
    }
}