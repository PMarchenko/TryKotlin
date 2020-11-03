package com.itdroid.pocketkotlin.syntax.model

import com.itdroid.pocketkotlin.syntax.model.SyntaxMarker

/**
 * @author Pavel Marchenko
 */
internal data class SyntaxToken(
    val range: IntRange,
    val marker: SyntaxMarker
)