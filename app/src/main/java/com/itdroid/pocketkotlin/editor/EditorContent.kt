package com.itdroid.pocketkotlin.editor

import androidx.compose.foundation.AmbientContentColor
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
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
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.ui.compose.AppImage
import com.itdroid.pocketkotlin.ui.compose.Empty
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.preferences.LogsPanelWeightPreference
import com.itdroid.pocketkotlin.projects.projectExamples
import com.itdroid.pocketkotlin.settings.preferences
import com.itdroid.pocketkotlin.utils.ImageInput
import com.itdroid.pocketkotlin.utils.tint

/**
 * @author itdroid
 */
@Composable
fun EditorContent(
    editorInfo: EditorInfo,
    logsAsTab: Boolean,
) {
    if (logsAsTab) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            ContentUi(editorInfo)
        }
    } else {
        var contentSize by remember { mutableStateOf(IntSize(0, 0)) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { contentSize = it }
        ) {
            val prefs = preferences()

            val showLogs by prefs.showLogsPanelObservable.observeAsState(false)
            val logsToggleAction: (Boolean) -> Unit = {
                prefs.showLogsPanel = it
            }

            val logsPanelWeight by prefs.logsPanelWeightObservable
                .observeAsState(LogsPanelWeightPreference.DEFAULT)
            val editorWeight = LogsPanelWeightPreference.MAX -
                    if (showLogs) logsPanelWeight else 0f
            val panelsOrientation =
                if (integerResource(id = R.integer.editor__orientation) == 0) Orientation.Vertical
                else Orientation.Horizontal
            val logsSizeChangeAction: (Float) -> Unit = { delta ->
                val size =
                    if (panelsOrientation == Orientation.Vertical) contentSize.height
                    else contentSize.width
                if (size > 0) {
                    prefs.logsPanelWeight = logsPanelWeight - delta / size
                }
            }

            val logsWeight = if (showLogs) logsPanelWeight else 0f

            EditorContentUi(
                editorInfo = editorInfo,
                orientation = panelsOrientation,
                editorWeight = editorWeight,
                logsWeight = logsWeight,
                logsVisible = showLogs,
                logsSizeChangeAction = logsSizeChangeAction,
                logsVisibilityAction = logsToggleAction,
            )
        }
    }
}

@Composable
private fun EditorContentUi(
    editorInfo: EditorInfo,
    orientation: Orientation,
    editorWeight: Float,
    logsWeight: Float,
    logsVisible: Boolean,
    logsSizeChangeAction: (Float) -> Unit,
    logsVisibilityAction: (Boolean) -> Unit,
) {
    EditorContentPanels(
        orientation = orientation,
        editorWeight = editorWeight,
        logsWeight = logsWeight,
        logsVisible = logsVisible,
        logsVisibilityAction = logsVisibilityAction,
        editor = { ContentUi(editorInfo) }
    ) {
        LogsContent(
            leadingContent = { DragIcon(it) { delta -> logsSizeChangeAction(delta) } },
        )
    }
}

@Composable
private fun ContentUi(editorInfo: EditorInfo) {
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
    editorWeight: Float,
    logsWeight: Float,
    logsVisible: Boolean,

    logsVisibilityAction: (Boolean) -> Unit,

    editor: @Composable () -> Unit,
    logs: @Composable (Orientation) -> Unit,
) {
    if (orientation == Orientation.Vertical) {
        Column(Modifier.fillMaxSize()) {
            Box(Modifier.weight(editorWeight)) {
                editor()
            }
            if (logsVisible) {
                Box(Modifier.weight(logsWeight)) {
                    logs(orientation)
                }
            }
            EditorTools(
                logsVisible = logsVisible,
                logsVisibilityAction = logsVisibilityAction
            )
        }
    } else {
        Column(Modifier.fillMaxSize()) {
            Row(Modifier.weight(1f)) {
                Box(Modifier.weight(editorWeight)) {
                    editor()
                }
                if (logsVisible) {
                    Box(Modifier.weight(logsWeight)) {
                        logs(orientation)
                    }
                }
            }
            EditorTools(
                logsVisible = logsVisible,
                logsVisibilityAction = logsVisibilityAction
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

@Preview("EditorToolbar preview [Light Theme]")
@Composable
private fun ScreenEditorPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        EditorContentUi(
            editorInfo = EditorInfo(projectExamples[0]),
            orientation = Orientation.Vertical,
            editorWeight = 0.5f,
            logsWeight = 0.5f,
            logsVisible = true,
            logsSizeChangeAction = {},
            logsVisibilityAction = {},
        )
    }
}

@Preview("EditorToolbar preview [Dark Theme]")
@Composable
private fun ScreenEditorPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        EditorContentUi(
            editorInfo = EditorInfo(projectExamples[0]),
            orientation = Orientation.Vertical,
            editorWeight = 0.5f,
            logsWeight = 0.5f,
            logsVisible = true,
            logsSizeChangeAction = {},
            logsVisibilityAction = {},
        )
    }
}