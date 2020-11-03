package com.itdroid.pocketkotlin.syntax

import android.text.Editable
import android.text.Spannable
import com.itdroid.pocketkotlin.syntax.span.SyntaxSpanFactoryProvider
import com.itdroid.pocketkotlin.utils.eLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn

/**
 * @author itdroid
 */
internal class KotlinSyntaxRepository {

    private val syntaxService = KotlinSyntaxService()

    suspend fun analyze(
        program: Editable,
        spanFactoryProvider: SyntaxSpanFactoryProvider,
    ) {
        val start = System.nanoTime()
        var tokenNumber = 0
        syntaxService.analyze(program)
            .flowOn(Dispatchers.Default)
            .collect { token ->
                eLog { "Receive token ${tokenNumber++} after ${(System.nanoTime() - start) / 1e6}ms" }
                program.setSpan(
                    spanFactoryProvider.factoryFor(token.marker).create(),
                    token.range.first,
                    token.range.last,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
    }
}
