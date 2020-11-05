package com.itdroid.pocketkotlin.syntax

import android.text.Editable
import com.itdroid.pocketkotlin.syntax.model.SyntaxToken
import kotlinx.coroutines.flow.Flow

/**
 * @author itdroid
 */
internal interface SyntaxService {

    suspend fun analyze(program: Editable): Flow<SyntaxToken>
}
