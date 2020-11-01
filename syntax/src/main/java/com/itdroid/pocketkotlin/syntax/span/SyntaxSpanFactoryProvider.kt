package com.itdroid.pocketkotlin.syntax.span

import com.itdroid.pocketkotlin.syntax.ColorConfig
import com.itdroid.pocketkotlin.syntax.model.*

/**
 * @author itdroid
 */
internal class SyntaxSpanFactoryProvider(colors: ColorConfig) {

    private val keywordSpanFactory = KeywordSyntaxSpanFactory(colors.keywordColor)
    private val propNameSpanFactory = PropertySyntaxSpanFactory(colors.propNameColor)
    private val funNameSpanFactory = FunctionNameSyntaxSpanFactory(colors.funNameColor)
    private val strCharLiteralSpanFactory =
        StrCharLiteralSyntaxSpanFactory(colors.strCharLiteralColor)
    private val numberLiteralSpanFactory = NumberLiteralSyntaxSpanFactory(colors.numberLiteralColor)

    fun factoryFor(marker: SyntaxMarker): SyntaxSpanFactory =
        when (marker) {
            KeywordMarker -> keywordSpanFactory
            PropertyMarker -> propNameSpanFactory
            FunctionMarker -> funNameSpanFactory
            StrCharLiteralMarker -> strCharLiteralSpanFactory
            NumberMarker -> numberLiteralSpanFactory
        }
}
