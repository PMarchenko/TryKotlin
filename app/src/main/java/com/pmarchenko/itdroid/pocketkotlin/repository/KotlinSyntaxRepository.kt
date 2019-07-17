package com.pmarchenko.itdroid.pocketkotlin.repository

import android.text.Editable
import com.pmarchenko.itdroid.pocketkotlin.model.EditorError
import com.pmarchenko.itdroid.pocketkotlin.service.syntax.KotlinSyntaxService

/**
 * @author Pavel Marchenko
 */
class KotlinSyntaxRepository {
    companion object {

        val SYNTAX_SERVICE = KotlinSyntaxService()
    }

    fun highlightSyntax(programText: Editable,
                        errors: MutableList<EditorError>) {

        SYNTAX_SERVICE.highlightSyntax(programText, errors)
    }
}