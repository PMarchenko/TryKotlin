package com.itdroid.pocketkotlin.syntax.span

import com.itdroid.pocketkotlin.syntax.ColorConfig
import com.itdroid.pocketkotlin.syntax.model.*
import com.itdroid.pocketkotlin.utils.checkAllMatched

/**
 * @author itdroid
 */
internal class SyntaxSpanFactoryProvider(colors: ColorConfig) {

    private val docCommentSpanFactory = KeywordSyntaxSpanFactory(colors.docCommentColor)

    private val commentSpanFactory = KeywordSyntaxSpanFactory(colors.commentColor)

    private val keywordSpanFactory = KeywordSyntaxSpanFactory(colors.keywordColor)

    private val propNameSpanFactory = PropertySyntaxSpanFactory(colors.propNameColor)

    private val funNameSpanFactory = FunctionNameSyntaxSpanFactory(colors.funNameColor)

    private val strCharLiteralSpanFactory =
        StrCharLiteralSyntaxSpanFactory(colors.strCharLiteralColor)

    private val numberLiteralSpanFactory = NumberLiteralSyntaxSpanFactory(colors.numberLiteralColor)

    fun factoryFor(marker: SyntaxMarker): SyntaxSpanFactory =
        when (marker) {
            DocCommentMarker -> docCommentSpanFactory
            CommentMarker -> commentSpanFactory
            KeywordMarker -> keywordSpanFactory
            PropertyNameMarker -> propNameSpanFactory
            FunctionNameMarker -> funNameSpanFactory
            TextLiteralMarker -> strCharLiteralSpanFactory
            NumberLiteralMarker -> numberLiteralSpanFactory
        }.checkAllMatched
}
