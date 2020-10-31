package com.itdroid.pocketkotlin.syntax.span

import com.itdroid.pocketkotlin.syntax.ColorConfig

/**
 * @author itdroid
 */
internal class SyntaxSpanFactoryProvider(colors: ColorConfig) {

    val keywordSpanFactory = KeywordSyntaxSpanProvider(colors.keywordColor)

    val propNameSpanFactory = PropertySyntaxSpanProvider(colors.propNameColor)

    val funNameSpanFactory = FunctionNameSyntaxSpanProvider(colors.funNameColor)

    val strCharLiteralSpanFactory = StrCharLiteralSyntaxSpanProvider(colors.strCharLiteralColor)

    val numberLiteralSpanFactory = NumberLiteralSyntaxSpanProvider(colors.numberLiteralColor)
}
