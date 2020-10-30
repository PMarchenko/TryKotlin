package com.pmarchenko.itdroid.pocketkotlin.syntax

import android.text.Editable
import com.pmarchenko.itdroid.pocketkotlin.syntax.model.SyntaxInfo

/**
 * @author Pavel Marchenko
 */
internal interface SyntaxService {

    fun analyze(program: Editable): SyntaxInfo
}