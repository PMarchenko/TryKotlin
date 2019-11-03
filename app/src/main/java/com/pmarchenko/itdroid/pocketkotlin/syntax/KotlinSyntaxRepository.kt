package com.pmarchenko.itdroid.pocketkotlin.syntax

import android.text.Editable
import com.pmarchenko.itdroid.pocketkotlin.data.model.EditorError

/**
 * @author Pavel Marchenko
 */
class KotlinSyntaxRepository(private val syntaxService: KotlinSyntaxService) {

    fun highlightSyntax(programText: Editable, errors: MutableList<EditorError>) {

        syntaxService.highlightSyntax(programText, errors)
    }
}