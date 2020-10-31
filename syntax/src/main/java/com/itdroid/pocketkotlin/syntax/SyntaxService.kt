package com.itdroid.pocketkotlin.syntax

import android.text.Editable
import com.itdroid.pocketkotlin.syntax.model.SyntaxInfo

/**
 * @author itdroid
 */
internal interface SyntaxService {

    fun analyze(program: Editable): SyntaxInfo
}