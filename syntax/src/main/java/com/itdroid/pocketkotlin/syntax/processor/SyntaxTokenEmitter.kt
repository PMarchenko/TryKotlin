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
            emit(stringLiteralStart..position, TextLiteralMarker)
        }
    }

    override suspend fun maybeStringLiteralExpressionEnd(position: Int) {
        if (stringLiteralExpressionStart >= 0) {
            stringLiteralExpressionStart = -1
            stringLiteralStart = position
        }
    }

    override suspend fun onStringLiteralEnd(position: Int) {
        if (stringLiteralStart >= 0) {
            emit(stringLiteralStart..position, TextLiteralMarker)
            stringLiteralStart = -1
        }
    }

    override suspend fun onCharLiteral(range: IntRange) {
        emit(range, TextLiteralMarker)
    }

    override suspend fun onNumberLiteral(range: IntRange) {
        emit(range, NumberLiteralMarker)
    }

    override suspend fun onFunctionName(range: IntRange) {
        emit(range, FunctionNameMarker)
    }

    override suspend fun onPropertyName(range: IntRange) {
        emit(range, PropertyNameMarker)
    }

    override suspend fun onAnnotation(range: IntRange) {
        emit(range, AnnotationMarker)
    }

    private suspend fun emit(range: IntRange, marker: SyntaxMarker) {
        collector.emit(SyntaxToken(range, marker))
    }
}
