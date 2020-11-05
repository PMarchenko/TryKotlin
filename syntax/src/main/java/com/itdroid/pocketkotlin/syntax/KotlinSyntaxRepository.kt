package com.itdroid.pocketkotlin.syntax

import android.text.Editable
import android.text.Spannable
import com.itdroid.pocketkotlin.syntax.model.MappingHolder
import com.itdroid.pocketkotlin.syntax.span.SyntaxSpanFactoryProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn

/**
 * @author itdroid
 */
internal class KotlinSyntaxRepository {

    private val syntaxService = KotlinSyntaxService()

    private val mappingHolder = MappingHolder()

    suspend fun analyze(
        syntaxMappingId: Long,
        program: Editable,
        spanFactoryProvider: SyntaxSpanFactoryProvider,
    ) {
        val currentSyntax = mappingHolder.get(syntaxMappingId)
        val currentRanges = currentSyntax.ranges()
        val allSpans = program.getSpans(0, program.length, Any::class.java)

        syntaxService.analyze(program)
            .flowOn(Dispatchers.Default)
            .collect { (range, marker) ->
                currentRanges.remove(range)
                if (currentSyntax.getMarker(range) != marker) {
                    currentSyntax.getSpan(range)?.let { program.removeSpan(it) }

                    val span = spanFactoryProvider.factoryFor(marker).create()
                    currentSyntax.add(range, marker, span)
                    program.setSpan(span, range)
                } else {
                    currentSyntax.getSpan(range)?.let { span ->
                        if (!allSpans.contains(span)) {
                            program.setSpan(span, range)
                        }
                    }
                }
            }

        //clean up spans which were not emitted this pass
        currentRanges
            .mapNotNull { currentSyntax.remove(it) }
            .forEach { program.removeSpan(it) }
    }
}

private fun Editable.setSpan(span: Any, range: IntRange) {
    setSpan(span, range.first, range.last, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
}
