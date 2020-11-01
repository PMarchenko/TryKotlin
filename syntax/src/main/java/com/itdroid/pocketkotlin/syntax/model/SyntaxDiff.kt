package com.itdroid.pocketkotlin.syntax.model

/**
 * @author Pavel Marchenko
 */
class SyntaxDiff {

    val spansToDelete = mutableListOf<Any>()

    val spansToAdd = mutableMapOf<IntRange, Any>()

}