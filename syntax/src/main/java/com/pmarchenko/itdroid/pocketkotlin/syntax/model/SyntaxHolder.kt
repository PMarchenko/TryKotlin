package com.pmarchenko.itdroid.pocketkotlin.syntax.model

/**
 * @author Pavel Marchenko
 */
internal class SyntaxHolder {

    private val _spans = mutableListOf<Any>()

    val spans = listOf<Any>()

    fun add(span: Any) {
        _spans.add(span)
    }
}
