package com.itdroid.pocketkotlin.syntax

import android.text.Editable
import android.text.Spannable
import com.itdroid.pocketkotlin.syntax.model.MappingHolder
import com.itdroid.pocketkotlin.syntax.model.SyntaxMapping
import com.itdroid.pocketkotlin.syntax.span.SyntaxSpanFactoryProvider
import com.itdroid.pocketkotlin.utils.iLog
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

        syntaxService.analyze(program)
            .flowOn(Dispatchers.Default)
            .collect { (range, marker) ->
                currentRanges.remove(range)
                if (currentSyntax.getMarker(range) != marker) {
                    iLog { "Add span for $range" }
                    currentSyntax.getSpan(range)?.let { program.removeSpan(it) }

                    val span = spanFactoryProvider.factoryFor(marker).create()
                    currentSyntax.add(range, marker, span)
                    program
                        .setSpan(span, range.first, range.last, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
            }

        //clean up spans which were not emitted this pass
        currentRanges
            .mapNotNull {
                iLog { "Remove span for $it" }
                currentSyntax.remove(it)
            }
            .forEach { program.removeSpan(it) }
    }
}

/**
 * We declare a package-level function main which returns Unit and takes
 * an Array of strings as a parameter. Note that semicolons are optional.
 */

fun main(args: Array<String>) {
    println("Hello, world!")
}