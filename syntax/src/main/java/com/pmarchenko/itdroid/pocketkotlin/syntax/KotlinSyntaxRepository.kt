package com.pmarchenko.itdroid.pocketkotlin.syntax

import android.text.Editable

/**
 * @author Pavel Marchenko
 */
class KotlinSyntaxRepository {

    private val syntaxService = KotlinSyntaxService()

    fun highlightSyntax(programText: Editable) {
        syntaxService.highlightSyntax(programText)
    }
}