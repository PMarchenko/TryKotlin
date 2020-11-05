package com.itdroid.pocketkotlin.syntax.span

import com.itdroid.pocketkotlin.syntax.SyntaxColorConfig
import com.itdroid.pocketkotlin.syntax.model.*
import com.itdroid.pocketkotlin.utils.checkAllMatched

/**
 * @author itdroid
 */
internal class SyntaxSpanFactoryProvider(colors: SyntaxColorConfig) {

    private val docCommentSpanFactory = SyntaxSpanFactory(colors.docCommentColor)

    private val commentSpanFactory = SyntaxSpanFactory(colors.commentColor)

    private val keywordSpanFactory = SyntaxSpanFactory(colors.keywordColor)

    private val propNameSpanFactory = SyntaxSpanFactory(colors.propNameColor)

    private val funNameSpanFactory = SyntaxSpanFactory(colors.funNameColor)

    private val strCharLiteralSpanFactory = SyntaxSpanFactory(colors.strCharLiteralColor)

    private val numberLiteralSpanFactory = SyntaxSpanFactory(colors.numberLiteralColor)

    private val annotationSpanFactory = SyntaxSpanFactory(colors.annotationColor)

    private val atSuffixSpanFactory = SyntaxSpanFactory(colors.atSuffixColor)

    fun factoryFor(marker: SyntaxMarker): SyntaxSpanFactory =
        when (marker) {
            DocCommentMarker -> docCommentSpanFactory
            CommentMarker -> commentSpanFactory
            KeywordMarker -> keywordSpanFactory
            PropertyNameMarker -> propNameSpanFactory
            FunctionNameMarker -> funNameSpanFactory
            TextLiteralMarker -> strCharLiteralSpanFactory
            NumberLiteralMarker -> numberLiteralSpanFactory
            AnnotationMarker -> annotationSpanFactory
            AtSuffixMarker -> atSuffixSpanFactory
        }.checkAllMatched
}
