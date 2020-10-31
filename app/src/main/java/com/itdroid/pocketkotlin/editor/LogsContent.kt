package com.itdroid.pocketkotlin.editor

import android.text.Spannable
import androidx.compose.animation.asDisposableClock
import androidx.compose.foundation.*
import androidx.compose.foundation.animation.defaultFlingConfig
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.AnimationClockAmbient
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.ui.compose.AppCheckBox
import com.itdroid.pocketkotlin.ui.compose.AppDivider
import com.itdroid.pocketkotlin.ui.compose.AppImageButton
import com.itdroid.pocketkotlin.ui.compose.logsConfig
import com.itdroid.pocketkotlin.editor.logs.*
import com.itdroid.pocketkotlin.utils.TextInput
import com.itdroid.pocketkotlin.utils.checkAllMatched
import com.itdroid.pocketkotlin.utils.measureExecutionTime

/**
 * @author itdroid
 */
@Composable
fun LogsContent(
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    Surface(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 2.dp
    ) {
        Column(Modifier.fillMaxSize()) {
            val vm = viewModel<EditorViewModel>()
            val logs by vm.appLogger.observeAsState(emptyList())

            val logsState: MutableState<LogsState> = savedInstanceState { LogsState() }
            val listState = createListSavedState(logsState, logs.size)

            LogsContentHeader(
                logsState = logsState,
                listState = listState,
                leadingContent = leadingContent,
                trailingContent = trailingContent)

            AppDivider(modifier = Modifier.padding(top = 0.5f.dp))

            val colorConfig = MaterialTheme.colors.logsConfig

            LazyColumnFor(
                items = logs,
                modifier = Modifier.fillMaxSize(),
                state = listState
            ) { log ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(indication = null) {
                            if (logsState.value.autoScroll) {
                                logsState.value =
                                    logsState.value.copyWith(autoScroll = false, listState)
                            }
                        }
                ) {
                    val text = remember(log) { log.asAnnotatedString(colorConfig) }
                    Text(
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = text,
                        style = AmbientTextStyle.current.copy(fontFamily = FontFamily.Monospace),
                    )
                }
            }
        }
    }
}

@Composable
private fun LogsContentHeader(
    logsState: MutableState<LogsState>,
    listState: LazyListState,
    leadingContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        leadingContent?.invoke()
        LogsListHeaderScrollableContent(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            logsState = logsState,
            listState = listState
        )
        trailingContent?.invoke()
    }
}

@Composable
private fun LogsListHeaderScrollableContent(
    modifier: Modifier = Modifier,
    logsState: MutableState<LogsState>,
    listState: LazyListState,
) {
    ScrollableRow(
        modifier = modifier
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = stringResource(R.string.screen__project_editor__label__logs))

        Spacer(modifier = Modifier.weight(1f))

        AppCheckBox(
            modifier = Modifier.padding(start = 8.dp),
            checked = logsState.value.autoScroll,
            text = TextInput(R.string.screen__project_editor__label__logs_auto_scroll)
        ) {
            logsState.value = logsState.value.copyWith(autoScroll = it, listState)
        }

        val editorViewModel = viewModel<EditorViewModel>()
        AppImageButton(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = TextInput(R.string.screen__project_editor__label__clear_logs),
        ) {
            editorViewModel.clearLogs()
        }
    }
}

@Composable
private fun createListSavedState(
    logsState: MutableState<LogsState>,
    logSize: Int,
): LazyListState {
    val autoScroll = logsState.value.autoScroll
    val firstVisibleItemIndex =
        if (autoScroll) {
            logSize - 1
        } else {
            logsState.value.firstVisibleItemIndex
        }

    val firstVisibleItemScrollOffset =
        if (autoScroll) {
            Integer.MAX_VALUE
        } else {
            logsState.value.firstVisibleItemScrollOffset
        }
    val config = defaultFlingConfig()
    val clock = AnimationClockAmbient.current.asDisposableClock()

    return remember(
        inputs = arrayOf(autoScroll, if (autoScroll) System.nanoTime() else 0)
    ) {
        LazyListState(firstVisibleItemIndex, firstVisibleItemScrollOffset, config, clock)
    }
}

private fun Spannable.asAnnotatedString(colorConfig: LogsColorConfig): AnnotatedString {
    val out = AnnotatedString.Builder(toString())
    measureExecutionTime {
        for (span in getSpans(0, length, Span::class.java)) {
            out.addStyle(
                SpanStyle(
                    when (span) {
                        is ErrorSpan -> colorConfig.error
                        is WarningSpan -> colorConfig.warning
                        is SuccessSpan -> colorConfig.success
                    }.checkAllMatched
                ),
                getSpanStart(span),
                getSpanEnd(span)
            )
        }
    }
    return out.toAnnotatedString()
}

