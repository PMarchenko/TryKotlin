package com.pmarchenko.itdroid.pocketkotlin.syntax

import android.text.Editable
import com.pmarchenko.itdroid.pocketkotlin.model.EditorError
import com.pmarchenko.itdroid.pocketkotlin.syntax.KotlinSyntaxService

/**
 * @author Pavel Marchenko
 */
class KotlinSyntaxRepository(private val syntaxService: KotlinSyntaxService) {

    fun highlightSyntax(programText: Editable, errors: MutableList<EditorError>) {

        syntaxService.highlightSyntax(programText, errors)
    }
}