package com.itdroid.pocketkotlin.syntax.processor

import com.itdroid.pocketkotlin.syntax.model.*
import kotlinx.coroutines.flow.FlowCollector

/**
 * @author Pavel Marchenko
 */
internal class SyntaxTokenEmitter(
    private val collector: FlowCollector<SyntaxToken>,
) : KotlinSyntaxListener {

    private var stringLiteralStart: Int = -1
    private var stringLiteralExpressionStart: Int = -1

    override suspend fun onDocComment(range: IntRange) {
        emit(range, DocCommentMarker)
    }

    override suspend fun onComment(range: IntRange) {
        emit(range, CommentMarker)
    }

    override suspend fun onKeyword(range: IntRange) {
        emit(range, KeywordMarker)
    }

    override suspend fun onStringLiteralStart(position: Int) {
        stringLiteralStart = position
    }

    override suspend fun onStringLiteralExpressionStart(position: Int) {
        if (stringLiteralStart >= 0) {
            stringLiteralExpressionStart = position
            emit(stringLiteralStart..position, StrCharLiteralMarker)
        }
    }

    override suspend fun onStringLiteralExpressionEnd(position: Int) {
        if (stringLiteralExpressionStart >= 0) {
            stringLiteralExpressionStart = -1
            stringLiteralStart = position
        }
    }

    override suspend fun onStringLiteralEnd(position: Int) {
        if (stringLiteralStart >= 0) {
            emit(stringLiteralStart..position, StrCharLiteralMarker)
            stringLiteralStart = -1
        }
    }

    override suspend fun onCharLiteral(range: IntRange) {
        emit(range, StrCharLiteralMarker)
    }

    override suspend fun onNumberLiteral(range: IntRange) {
        emit(range, NumberMarker)
    }

    private suspend fun emit(range: IntRange, marker: SyntaxMarker) {
        collector.emit(SyntaxToken(range, marker))
    }
}
