package com.itdroid.pocketkotlin.syntax.model

/**
 * @author Pavel Marchenko
 */
internal class SyntaxMapping {

    private val syntax = mutableMapOf<IntRange, Record>()

    fun add(range: IntRange, marker: SyntaxMarker, span: Any) {
        syntax[range] = Record(marker, span)
    }

    fun ranges(): MutableSet<IntRange> = syntax.keys.toMutableSet()

    fun getMarker(range: IntRange): SyntaxMarker? = syntax[range]?.marker

    fun getSpan(range: IntRange): Any? = syntax[range]?.span

    fun remove(range: IntRange): Any? = syntax.remove(range)?.span

}

private data class Record(
    val marker: SyntaxMarker,
    val span: Any,
)