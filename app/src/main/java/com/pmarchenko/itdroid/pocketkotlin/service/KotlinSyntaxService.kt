package com.pmarchenko.itdroid.pocketkotlin.service

import android.text.Editable
import android.text.Layout
import android.text.Spanned
import com.pmarchenko.itdroid.pocketkotlin.editor.syntax.ErrorSpan
import com.pmarchenko.itdroid.pocketkotlin.editor.syntax.SyntaxSpans
import com.pmarchenko.itdroid.pocketkotlin.editor.syntax.WarningSpan
import com.pmarchenko.itdroid.pocketkotlin.extentions.measure
import com.pmarchenko.itdroid.pocketkotlin.model.ErrorSeverity
import com.pmarchenko.itdroid.pocketkotlin.model.ProjectError

/**
 * @author Pavel Marchenko
 */
class KotlinSyntaxService {

    fun highlightSyntax(editableText: Editable, layout: Layout, errors: MutableList<ProjectError>) {
        measure("EditorView") {
            val spans = prepareSyntaxSpans(layout, errors)
            clearSyntax(editableText)
            applySyntax(spans, editableText)
        }
    }

    private fun prepareSyntaxSpans(layout: Layout, errors: MutableList<ProjectError>): SyntaxSpans {
        val out = SyntaxSpans()
        errors.forEach { error ->
            val span = getSpanForError(error)
            span.start = layout.getLineStart(error.interval.start.line) + error.interval.start.ch
            span.end = layout.getLineStart(error.interval.end.line) + error.interval.end.ch
            span.flags = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            out.add(span)
        }
        return out
    }

    @Suppress("REDUNDANT_ELSE_IN_WHEN")
    private fun getSpanForError(error: ProjectError) = when (error.severity) {
        ErrorSeverity.ERROR -> ErrorSpan()
        ErrorSeverity.WARNING -> WarningSpan()
        else -> TODO("Unsupported error severity: ${error.severity}")
    }

    private fun clearSyntax(editableText: Editable) {
        val oldSpans = editableText.getSpans(0, editableText.length, SyntaxSpans::class.java)
        oldSpans.forEach { holders ->
            holders.forEach { holder ->
                editableText.removeSpan(holder.span)
            }
        }
        editableText.removeSpan(oldSpans)
    }

    private fun applySyntax(spans: SyntaxSpans, editableText: Editable) {
        spans.forEach {
            editableText.setSpan(it.span, it.start, it.end, it.flags)
        }
        editableText.setSpan(spans, 0, 0, Spanned.SPAN_MARK_MARK)
    }
}