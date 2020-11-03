package com.itdroid.pocketkotlin.syntax.span

import android.text.style.ForegroundColorSpan

/**
 * @author itdroid
 */
internal interface SyntaxSpanFactory {

    fun create(): Any
}

internal class KeywordSyntaxSpanFactory(private val color: Int) : SyntaxSpanFactory {

    override fun create() = ForegroundColorSpan(color)
}

internal class PropertySyntaxSpanFactory(private val color: Int) : SyntaxSpanFactory {

    override fun create() = ForegroundColorSpan(color)
}

internal class FunctionNameSyntaxSpanFactory(private val color: Int) : SyntaxSpanFactory {

    override fun create() = ForegroundColorSpan(color)
}

internal class StrCharLiteralSyntaxSpanFactory(private val color: Int) : SyntaxSpanFactory {

    override fun create() = ForegroundColorSpan(color)
}

internal class NumberLiteralSyntaxSpanFactory(private val color: Int) : SyntaxSpanFactory {

    override fun create() = ForegroundColorSpan(color)
}
