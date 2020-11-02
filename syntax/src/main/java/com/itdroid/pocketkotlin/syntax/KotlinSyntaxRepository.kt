package com.itdroid.pocketkotlin.syntax

import android.text.Editable
import android.text.Spannable
import com.itdroid.pocketkotlin.syntax.model.Syntax
import com.itdroid.pocketkotlin.syntax.model.SyntaxDiff
import com.itdroid.pocketkotlin.syntax.model.SyntaxMarker
import com.itdroid.pocketkotlin.syntax.span.SyntaxSpanFactoryProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author itdroid
 */
internal class KotlinSyntaxRepository {

    private val syntaxService = KotlinSyntaxService()

    private val syntaxStore: MutableMap<Long, Syntax> = mutableMapOf()

    suspend fun analyze(
        syntaxId: Long,
        program: Editable,
        range: IntRange,
        spanFactoryProvider: SyntaxSpanFactoryProvider,
    ) {
        val update: Map<IntRange, SyntaxMarker> = syntaxService.analyze(program, range)

        val syntax: Syntax = syntaxStore.getOrPut(syntaxId) { Syntax() }
        val diff = syntax.apply(update, range, spanFactoryProvider)

        syntax.sync(program, diff)

        withContext(Dispatchers.Main) {
            program.apply(diff)
        }
    }
}

private fun Editable.apply(diff: SyntaxDiff) {
    for (span in diff.spansToDelete) {
        removeSpan(span)
    }

    for ((range, span) in diff.spansToAdd) {
        setSpan(span, range.first, range.last, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
}
