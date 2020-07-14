package com.pmarchenko.itdroid.pocketkotlin.syntax

import android.text.Editable
import android.text.Spanned
import com.pmarchenko.itdroid.pocketkotlin.utils.measureTimeAndLog

/**
 * @author Pavel Marchenko
 */
class KotlinSyntaxService {

    fun highlightSyntax(program: Editable) {
        measureTimeAndLog ("KProgramTokenizer2") {
            highlightSyntaxInternal(program)
        }
    }

    private fun highlightSyntaxInternal(program: Editable) {
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
        val syntax = prepareSyntax(program)
        for (token in syntax) {
            program.setSpan(token.span, token.start, token.end, token.flags)
        }
        program.setSpan(syntax, 0, 0, Spanned.SPAN_MARK_MARK)
    }

    private fun prepareSyntax(program: CharSequence): Syntax {
        val out = Syntax()

        //todo analyze program

        return out
    }
}