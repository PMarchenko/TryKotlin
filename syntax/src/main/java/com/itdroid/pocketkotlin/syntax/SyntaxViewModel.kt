package com.itdroid.pocketkotlin.syntax

import android.text.Editable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itdroid.pocketkotlin.syntax.span.SyntaxSpanFactoryProvider
import com.itdroid.pocketkotlin.utils.ThrottleExecutor
import kotlinx.coroutines.Dispatchers
import java.util.*

/**
 * @author itdroid
 */
class SyntaxViewModel : ViewModel() {

    private val syntaxRepo = KotlinSyntaxRepository()

    private val executor = ThrottleExecutor.forScope(viewModelScope, 0L)

    fun highlightSyntax(fileId: Long, program: Editable, colors: SyntaxColorConfig) {
        executor.post(Dispatchers.Main) {
            syntaxRepo.analyze(
                syntaxMappingId = Objects.hash(fileId, colors.isLightColors),
                program = program,
                spanFactoryProvider = SyntaxSpanFactoryProvider(colors)
            )
        }
    }
}
