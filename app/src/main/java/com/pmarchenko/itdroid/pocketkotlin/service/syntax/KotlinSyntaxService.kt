package com.pmarchenko.itdroid.pocketkotlin.service.syntax

import android.text.Editable
import android.text.Spanned
import com.pmarchenko.itdroid.pocketkotlin.model.EditorError
import com.pmarchenko.itdroid.pocketkotlin.model.project.ErrorSeverity
import com.pmarchenko.itdroid.pocketkotlin.utils.measure

/**
 * @author Pavel Marchenko
 */
class KotlinSyntaxService {

    fun highlightSyntax(program: Editable, errors: MutableList<EditorError>) {
        measure("KProgramTokenizer2") {
            highlightSyntaxInternal(program, errors)
        }
    }

    private fun highlightSyntaxInternal(program: Editable, errors: MutableList<EditorError>) {
        val syntax = prepareSyntax(program, errors)

        //todo reuse syntax
        //clear old syntax
        program.getSpans(0, program.length, Syntax::class.java).forEach {
            it.forEach { syntaxToken -> program.removeSpan(syntaxToken.span) }
            program.removeSpan(it)
        }

        //apply new syntax
        syntax.forEach { program.setSpan(it.span, it.start, it.end, it.flags) }
        program.setSpan(syntax, 0, 0, Spanned.SPAN_MARK_MARK)
    }

    private fun prepareSyntax(program: CharSequence, errors: MutableList<EditorError>): Syntax {
        val out = Syntax()

        errors.forEach { error ->
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