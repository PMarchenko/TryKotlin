package com.itdroid.pocketkotlin.syntax.span

import android.text.style.ForegroundColorSpan

/**
 * @author itdroid
 */
internal class KeywordSpan(color: Int) : ForegroundColorSpan(color)

internal class VariableNameSpan(color: Int) : ForegroundColorSpan(color)

internal class FunctionNameSpan(color: Int) : ForegroundColorSpan(color)

internal class StrCharLiteralSpan(color: Int) : ForegroundColorSpan(color)

internal class NumberLiteralSpan(color: Int) : ForegroundColorSpan(color)