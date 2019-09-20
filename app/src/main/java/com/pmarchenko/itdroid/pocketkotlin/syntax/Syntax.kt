package com.pmarchenko.itdroid.pocketkotlin.syntax

import android.graphics.Color
import android.text.Spanned
import android.text.style.ForegroundColorSpan

/**
 * @author Pavel Marchenko
 */
class Syntax(private val tokens: MutableList<SyntaxTokenHolder> = mutableListOf()) : MutableList<SyntaxTokenHolder> by tokens

abstract class SyntaxTokenHolder(
        val span: Any,
        var start: Int,
        var end: Int,
        var flags: Int = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
)

class ErrorSpan(start: Int, end: Int) : SyntaxTokenHolder(
    ColorUnderlineSpan(
        Color.parseColor("#BBEC5424")
    ), start, end)

class WarningSpan(start: Int, end: Int) : SyntaxTokenHolder(
    ColorUnderlineSpan(
        Color.parseColor("#BBFFC107")
    ), start, end)

class KeywordSpan(start: Int, end: Int) : SyntaxTokenHolder(ForegroundColorSpan(Color.parseColor("#CC7832")), start, end)

class Comment(start: Int, end: Int) : SyntaxTokenHolder(ForegroundColorSpan(Color.parseColor("#6A6A6A")), start, end)

class JavaDocComment(start: Int, end: Int) : SyntaxTokenHolder(ForegroundColorSpan(Color.parseColor("#629755")), start, end)

class StringLiteralSpan(start: Int, end: Int) : SyntaxTokenHolder(ForegroundColorSpan(Color.parseColor("#629755")), start, end)

class NumberSpan(start: Int, end: Int) : SyntaxTokenHolder(ForegroundColorSpan(Color.parseColor("#5896A6")), start, end)

class TestHighlight(start: Int, end: Int) : SyntaxTokenHolder(
    ColorUnderlineSpan(
        Color.parseColor("#BBAAAAAA")
    ), start, end)