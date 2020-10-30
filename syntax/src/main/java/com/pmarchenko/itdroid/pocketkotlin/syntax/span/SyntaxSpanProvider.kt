package com.pmarchenko.itdroid.pocketkotlin.syntax.span

import android.text.style.ForegroundColorSpan

/**
 * @author Pavel Marchenko
 */
internal interface SyntaxSpanProvider {

    fun create(): Any
}

internal class KeywordSyntaxSpanProvider(private val color: Int) : SyntaxSpanProvider {

    override fun create() = ForegroundColorSpan(color)
}

internal class PropertySyntaxSpanProvider(private val color: Int) : SyntaxSpanProvider {

    override fun create() = ForegroundColorSpan(color)
}

internal class FunctionNameSyntaxSpanProvider(private val color: Int) : SyntaxSpanProvider {

    override fun create() = ForegroundColorSpan(color)
}

internal class StrCharLiteralSyntaxSpanProvider(private val color: Int) : SyntaxSpanProvider {

    override fun create() = ForegroundColorSpan(color)
}

internal class NumberLiteralSyntaxSpanProvider(private val color: Int) : SyntaxSpanProvider {

    override fun create() = ForegroundColorSpan(color)
}