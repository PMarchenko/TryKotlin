package com.pmarchenko.itdroid.pocketkotlin.editor.syntax

import android.graphics.Color

/**
 * @author Pavel Marchenko
 */
class SyntaxSpans(private val spans: MutableList<SyntaxSpan> = mutableListOf()) : MutableList<SyntaxSpan> by spans

abstract class SyntaxSpan(val span: Any) {

    var start: Int = -1

    var end: Int = -1

    var flags: Int = -1
}

class ErrorSpan : SyntaxSpan(ColorUnderlineSpan(Color.parseColor("#EC5424")))

class WarningSpan : SyntaxSpan(ColorUnderlineSpan(Color.parseColor("#FFC107")))