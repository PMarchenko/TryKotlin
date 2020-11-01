package com.itdroid.pocketkotlin.syntax

import android.text.Editable
import com.itdroid.pocketkotlin.syntax.model.SyntaxMarker

/**
 * @author itdroid
 */
internal interface SyntaxService {

    fun analyze(program: Editable, range: IntRange): Map<IntRange, SyntaxMarker>
}
