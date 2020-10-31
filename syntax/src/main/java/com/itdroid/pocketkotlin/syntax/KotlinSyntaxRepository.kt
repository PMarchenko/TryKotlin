package com.itdroid.pocketkotlin.syntax

import android.text.Editable
import android.text.Spannable
import com.itdroid.pocketkotlin.syntax.model.SyntaxHolder
import com.itdroid.pocketkotlin.syntax.model.SyntaxInfo
import com.itdroid.pocketkotlin.syntax.span.SyntaxSpanFactoryProvider
import com.itdroid.pocketkotlin.syntax.span.SyntaxSpanProvider
import com.itdroid.pocketkotlin.utils.rangeEnd
import com.itdroid.pocketkotlin.utils.rangeStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author itdroid
 */
internal class KotlinSyntaxRepository {

    private val syntaxService = KotlinSyntaxService()

    suspend fun analyze(program: Editable, spanFactoryProvider: SyntaxSpanFactoryProvider) {
        val syntax = syntaxService.analyze(program)

        withContext(Dispatchers.Main) {
            program.apply(syntax, spanFactoryProvider)
        }
    }
}

private fun Editable.apply(syntax: SyntaxInfo, spanFactoryProvider: SyntaxSpanFactoryProvider) {
    cleanupSyntax()
    applySyntax(syntax, spanFactoryProvider)
}

private fun Editable.cleanupSyntax() {
    //TODO reuse spans
    val oldSyntaxStyles = getSpans(0, length, SyntaxHolder::class.java)
    oldSyntaxStyles
        .onEach(::removeSpan)
        .flatMap { it.spans }
        .forEach(::removeSpan)
}

private fun Editable.applySyntax(
    syntax: SyntaxInfo,
    spanFactoryProvider: SyntaxSpanFactoryProvider,
) {
    if (syntax.notEmpty) {
        val syntaxHolder = SyntaxHolder()

        applySyntax(
            ranges = syntax.keywordRanges,
            spanFactory = spanFactoryProvider.keywordSpanFactory,
            holder = syntaxHolder
        )

        applySyntax(
            ranges = syntax.propertyNames,
            spanFactory = spanFactoryProvider.propNameSpanFactory,
            holder = syntaxHolder
        )

        applySyntax(
            ranges = syntax.funNames,
            spanFactory = spanFactoryProvider.funNameSpanFactory,
            holder = syntaxHolder
        )

        applySyntax(
            ranges = syntax.strCharLiterals,
            spanFactory = spanFactoryProvider.strCharLiteralSpanFactory,
            holder = syntaxHolder
        )

        applySyntax(
            ranges = syntax.numberLiterals,
            spanFactory = spanFactoryProvider.numberLiteralSpanFactory,
            holder = syntaxHolder
        )

        setSpan(syntaxHolder, 0, 0, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
    }
}

private fun Editable.applySyntax(
    ranges: List<Long>,
    spanFactory: SyntaxSpanProvider,
    holder: SyntaxHolder,
) {
    for (range in ranges) {
        val span = spanFactory.create()
        holder.add(span)
        setSpan(what = span, where = range)
    }
}

fun Editable.setSpan(what: Any, where: Long) {
    setSpan(what, where.rangeStart(), where.rangeEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
}
