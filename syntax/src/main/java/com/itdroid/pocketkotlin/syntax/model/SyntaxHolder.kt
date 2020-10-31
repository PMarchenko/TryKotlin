package com.itdroid.pocketkotlin.syntax.model

/**
 * @author itdroid
 */
internal class SyntaxHolder {

    private val _spans = mutableListOf<Any>()

    val spans = listOf<Any>()

    fun add(span: Any) {
        _spans.add(span)
    }
}
