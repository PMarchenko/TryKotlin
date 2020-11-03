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

    private val spanFactoryProviderLightTheme = SyntaxSpanFactoryProvider(LightThemeColorConfig)
    private val spanFactoryProviderDarkTheme = SyntaxSpanFactoryProvider(DarkThemeColorConfig)

    fun highlightSyntax(fileId: Long, program: Editable, isLightTheme: Boolean) {
        executor.post(Dispatchers.Main) {
            syntaxRepo.analyze(
                program = program,
                spanFactoryProvider = if (isLightTheme) {
                    spanFactoryProviderLightTheme
                } else {
                    spanFactoryProviderDarkTheme
                }
            )
        }
    }
}
