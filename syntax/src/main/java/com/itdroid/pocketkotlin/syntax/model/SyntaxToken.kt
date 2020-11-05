package com.itdroid.pocketkotlin.syntax.model

/**
 * @author Pavel Marchenko
 */
internal data class SyntaxToken(
    val range: IntRange,
    val marker: SyntaxMarker,
)