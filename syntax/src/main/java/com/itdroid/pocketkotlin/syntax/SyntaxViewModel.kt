package com.itdroid.pocketkotlin.syntax

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itdroid.pocketkotlin.syntax.span.SyntaxSpanFactoryProvider
import com.itdroid.pocketkotlin.utils.ThrottleExecutor
import kotlinx.coroutines.Dispatchers

/**
 * @author itdroid
 */
class SyntaxViewModel : ViewModel() {

    private val syntaxRepo = KotlinSyntaxRepository()

    private val executor = ThrottleExecutor.forScope(viewModelScope, 0L)

    fun highlightSyntax(fileId: Long, program: Editable, range: IntRange, isLightTheme: Boolean) {
        val spanFactoryProvider = SyntaxSpanFactoryProvider(
            if (isLightTheme) LightThemeColorConfig
            else DarkThemeColorConfig
        )

        executor.post(Dispatchers.Default) {
            syntaxRepo.analyze(
                syntaxId = fileId,
                program = program,
                range = 0..program.length,
                spanFactoryProvider = spanFactoryProvider
            )
        }
    }
}
