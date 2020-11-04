package com.itdroid.pocketkotlin.syntax.processor

/**
 * @author Pavel Marchenko
 */
internal interface KotlinSyntaxListener {

    suspend fun onDocComment(range: IntRange)

    suspend fun onComment(range: IntRange)

    suspend fun onKeyword(range: IntRange)

    suspend fun onStringLiteralStart(position: Int)

    suspend fun onStringLiteralExpressionStart(position: Int)

    suspend fun onStringLiteralExpressionEnd(position: Int)

    suspend fun onStringLiteralEnd(position: Int)

    suspend fun onCharLiteral(range: IntRange)

    suspend fun onNumberLiteral(range: IntRange)
}