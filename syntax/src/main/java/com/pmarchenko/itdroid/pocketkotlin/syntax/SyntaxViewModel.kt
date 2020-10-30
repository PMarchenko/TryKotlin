package com.pmarchenko.itdroid.pocketkotlin.syntax

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmarchenko.itdroid.pocketkotlin.syntax.span.SyntaxSpanFactoryProvider
import com.pmarchenko.itdroid.pocketkotlin.utils.ThrottleExecutor
import kotlinx.coroutines.Dispatchers

/**
 * @author Pavel Marchenko
 */
class SyntaxViewModel : ViewModel() {

    private val syntaxRepo = KotlinSyntaxRepository()

    private val executor = ThrottleExecutor.forScope(viewModelScope, 0L)

    fun highlightSyntax(program: Editable, isLightTheme: Boolean) {
        val spanFactoryProvider = SyntaxSpanFactoryProvider(
            if (isLightTheme) LightThemeColorConfig
            else DarkThemeColorConfig
        )
        executor.post(Dispatchers.Default) {
            syntaxRepo.analyze(program, spanFactoryProvider)
        }
    }
}
