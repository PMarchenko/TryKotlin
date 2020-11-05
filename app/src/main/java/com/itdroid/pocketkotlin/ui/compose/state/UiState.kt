package com.itdroid.pocketkotlin.ui.compose.state

import androidx.compose.runtime.Composable
import com.itdroid.pocketkotlin.ui.compose.ErrorText
import com.itdroid.pocketkotlin.ui.compose.ProgressIndicator
import com.itdroid.pocketkotlin.utils.ErrorInput

/**
 * @author itdroid
 */
data class UiState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: ErrorInput? = null,
) {

    fun hasData() = data != null

    @Composable
    fun consumeData(consumer: @Composable (T) -> Unit) {
        if (data != null) consumer(data)
    }

    @Composable
    fun consumeLoading(consumer: @Composable () -> Unit) {
        if (isLoading) {
            consumer()
        }
    }

    @Composable
    fun consumeError(consumer: @Composable (ErrorInput) -> Unit) {
        if (error != null) consumer(error)
    }

    @Composable
    fun consume(
        loadingConsumer: @Composable () -> Unit = { ProgressIndicator() },
        errorConsumer: @Composable (ErrorInput) -> Unit = { ErrorText(text = it.error) },
        dataConsumer: @Composable ((T) -> Unit)? = null,
    ) {
        if (dataConsumer != null) consumeData(dataConsumer)
        consumeLoading(loadingConsumer)
        consumeError(errorConsumer)
    }
}

fun <T> defaultUiState(data: T? = null) = UiState(data)

fun <T> loadingUiState() = UiState<T>(isLoading = true)
