package com.itdroid.pocketkotlin.editor

import androidx.compose.animation.animate
import androidx.compose.foundation.AmbientContentColor
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.onSizeChanged
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.preferences.LogsPanelWeightPreference
import com.itdroid.pocketkotlin.projects.projectExamples
import com.itdroid.pocketkotlin.settings.preferences
import com.itdroid.pocketkotlin.ui.compose.AppImage
import com.itdroid.pocketkotlin.ui.compose.Empty
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.utils.ImageInput
import com.itdroid.pocketkotlin.utils.iLog
import com.itdroid.pocketkotlin.utils.tint

/**
 * @author itdroid
 */
@Composable
fun ScreenEditorContent(
    editorInfo: EditorInfo,
    withLogsPanel: Boolean,
    logsState: MutableState<Boolean>,
    toggleLogsAction: (Boolean) -> Unit,
) {
    if (withLogsPanel) {
        var contentSize by remember { mutableStateOf(IntSize(0, 0)) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { contentSize = it }
        ) {
            val prefs = preferences()
            val editorPanelWeight by prefs.logsPanelWeightObservable.observeAsState()

            editorPanelWeight?.let { logsWeight ->
                val panelsOrientation =
                    if (integerResource(id = R.integer.editor__orientation) == 0) Orientation.Vertical
                    else Orientation.Horizontal

                val logsSizeChangeAction: (Float) -> Unit = { delta ->
                    val size =
                        if (panelsOrientation == Orientation.Vertical) contentSize.height
                        else contentSize.width
                    if (size > 0) {
                        iLog { "delta: $delta / $size" }
                        prefs.logsPanelWeight = logsWeight - delta / size
                    }
                }

                EditorContentPanels(
                    orientation = panelsOrientation,
                    logsWeight = if (logsState.value) logsWeight else 0f,
                    toggleLogsAction = toggleLogsAction,

                    editor = { EditorUi(editorInfo) }
                ) {
                    val editorViewModel = viewModel<EditorViewModel>()

                    LogsContent(
                        leadingContent = { DragIcon(it) { delta -> logsSizeChangeAction(delta) } },
                        trailingContent = { ClearLogsIcon { editorViewModel.clearLogs() } }
                    )
                }
            }
        }
    } else {
        Box(Modifier.fillMaxSize()) {
            EditorUi(editorInfo)
        }
    }
}

@Composable
private fun EditorUi(editorInfo: EditorInfo) {
    if (editorInfo.project.files.isEmpty()) {
        Empty(R.string.screen__project_editor__label__no_files)
    } else {
        EditorFile(
            project = editorInfo.project,
            file = editorInfo.selectedFile(),
        )
    }
}

@Composable
private fun EditorContentPanels(
    orientation: Orientation,
    logsWeight: Float,

    toggleLogsAction: (Boolean) -> Unit,

    editor: @Composable () -> Unit,
    logs: @Composable (Orientation) -> Unit,
) {
    val animatedLogWeight = animate(logsWeight)
    val editorWeight = LogsPanelWeightPreference.MAX - animatedLogWeight
    val logsVisible = animatedLogWeight > 0f

    if (orientation == Orientation.Vertical) {
        Column(Modifier.fillMaxSize()) {
            Surface(
                modifier = Modifier
                    .weight(editorWeight)
                    .padding(all = 4.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = 2.dp
            ) {
                editor()
            }

            if (logsVisible) {
                Box(Modifier.weight(animatedLogWeight)) {
                    logs(orientation)
                }
            }

            EditorTools(
                logsVisible = logsVisible,
                toggleLogsAction = toggleLogsAction
            )
        }
    } else {
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.weight(1f)) {
                Surface(
                    modifier = Modifier
                        .weight(editorWeight)
                        .padding(all = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    elevation = 2.dp
                ) {
                    editor()
                }

                if (logsVisible) {
                    Box(Modifier.weight(animatedLogWeight)) {
                        logs(orientation)
                    }
                }
            }
            EditorTools(
                logsVisible = editorWeight < 1f,
                toggleLogsAction = toggleLogsAction
            )
        }
    }
}

@Composable
private fun DragIcon(
    orientation: Orientation,
    onDrag: Density.(Float) -> Unit,
) {
    Surface(
        color = Color.Transparent,
        shape = CircleShape
    ) {
        AppImage(
            image = ImageInput(vectorResId = R.drawable.ic__editor__drag_indicator)
                .tint(AmbientContentColor.current),
            modifier = Modifier
                .draggable(orientation = orientation, onDrag = onDrag)
                .clickable { }
                .padding(8.dp)
        )
    }
}

@Composable
private fun ClearLogsIcon(
    onClick: () -> Unit,
) {
    Surface(
        color = Color.Transparent,
        shape = CircleShape
    ) {
        AppImage(
            image = ImageInput(Icons.Default.Delete)
                .tint(AmbientContentColor.current),
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(8.dp)
        )
    }
}

@Preview("ScreenEditorContent preview [Light Theme]")
@Composable
private fun ScreenEditorContentPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        EditorContentPanels(
            orientation = Orientation.Vertical,
            logsWeight = 0.5f,
            toggleLogsAction = {},
            editor = {
                EditorFile(
                    project = projectExamples[0],
                    file = projectExamples[0].files[0]
                )
            },
            logs = {
                LogsContent()
            }
        )
    }
}

@Preview("ScreenEditorContent preview [Dark Theme]")
@Composable
private fun ScreenEditorContentPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        EditorContentPanels(
            orientation = Orientation.Vertical,
            logsWeight = 0.5f,
            toggleLogsAction = {},
            editor = {
                EditorFile(
                    project = projectExamples[0],
                    file = projectExamples[0].files[0]
                )
            },
            logs = {
                LogsContent()
            }
        )
    }
}