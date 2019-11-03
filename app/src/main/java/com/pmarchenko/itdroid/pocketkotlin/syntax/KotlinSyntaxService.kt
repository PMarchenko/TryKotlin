package com.pmarchenko.itdroid.pocketkotlin.syntax

import android.text.Editable
import android.text.Spanned
import com.pmarchenko.itdroid.pocketkotlin.data.model.EditorError
import com.pmarchenko.itdroid.pocketkotlin.data.model.project.ErrorSeverity
import com.pmarchenko.itdroid.pocketkotlin.domain.utils.measureTimeAndLog

/**
 * @author Pavel Marchenko
 */
class KotlinSyntaxService {

    fun highlightSyntax(program: Editable, errors: MutableList<EditorError>) {
        measureTimeAndLog("KProgramTokenizer2") {
            highlightSyntaxInternal(program, errors)
        }
    }

    private fun highlightSyntaxInternal(program: Editable, errors: MutableList<EditorError>) {
        //todo reuse syntax

        //clear old syntax
        val spans = program.getSpans(0, program.length, Syntax::class.java)
        for (syntax in spans) {
            for (token in syntax) {
                program.removeSpan(token.span)
            }
            program.removeSpan(syntax)
        }

        //apply new syntax
        val syntax = prepareSyntax(program, errors)
        for (token in syntax) {
            program.setSpan(token.span, token.start, token.end, token.flags)
        }
        program.setSpan(syntax, 0, 0, Spanned.SPAN_MARK_MARK)
    }

    private fun prepareSyntax(program: CharSequence, errors: MutableList<EditorError>): Syntax {
        val out = Syntax()

        errors.mapTo(out) { error ->
            @Suppress("REDUNDANT_ELSE_IN_WHEN")
            when (error.severity) {
                ErrorSeverity.ERROR -> ErrorSpan(error.start, error.end)
                ErrorSeverity.WARNING -> WarningSpan(error.start, error.end)
                else -> error("Unsupported error severity: ${error.severity}")
            }
        }

        //todo analyze program

        return out
    }
}