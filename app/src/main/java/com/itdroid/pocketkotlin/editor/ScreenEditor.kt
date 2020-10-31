package com.itdroid.pocketkotlin.editor

import androidx.compose.foundation.Icon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.navigation.navigation
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.projects.projectExamples
import com.itdroid.pocketkotlin.ui.compose.AppScreen
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.ui.compose.state.defaultUiState

/**
 * @author itdroid
 */
@Composable
fun ScreenEditor(
    projectId: Long,
    fileId: Long,
) {
    val vm = viewModel<EditorViewModel>()
    onActive {
        vm.loadProject(projectId, fileId)
    }
    vm.uiState
        .observeAsState(defaultUiState())
        .value
        .consume { editorInfo ->
            ScreenEditorUi(
                editorInfo = editorInfo,
                logsAsDrawer = booleanResource(id = R.bool.editor__logs_as_drawer),
                executeProjectAction = { vm.executeProject(editorInfo.project) },
            )
        }
}

@Composable
private fun ScreenEditorUi(
    editorInfo: EditorInfo,
    logsAsDrawer: Boolean,
    executeProjectAction: () -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        if (logsAsDrawer) {
            ScreenEditorContentWithDrawerUi(editorInfo)
        } else {
            val logsState = savedInstanceState { true }
            val toggleLogsAction: (Boolean) -> Unit = { logsState.value = it }
            ScreenEditorContentUi(
                editorInfo = editorInfo,
                withLogsPanel = true,
                logsState = logsState,
                toggleLogsAction = toggleLogsAction,
            )
        }

        if (editorInfo.project.files.isNotEmpty()) {
            if (editorInfo.isExecuting) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 24.dp, bottom = 24.dp),
                    color = MaterialTheme.colors.secondary
                )
            } else {
                FloatingActionButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 16.dp),
                    onClick = executeProjectAction,
                    icon = { Icon(Icons.Default.PlayArrow) },
                    elevation = 4.dp //TODO investigate why it crashes with IllegalStateException with default elevation
                )
            }
        }
    }
}

@Composable
private fun ScreenEditorContentUi(
    editorInfo: EditorInfo,
    withLogsPanel: Boolean,
    logsState: MutableState<Boolean>,
    toggleLogsAction: (Boolean) -> Unit,
) {
    AppScreen(
        toolbar = {
            ScreenEditorToolbar(
                editorInfo = editorInfo,
                logsAsDrawer = !withLogsPanel,
                toggleLogsAction = toggleLogsAction,
            )
        },
    ) {
        Box {
            ScreenEditorContent(
                withLogsPanel = withLogsPanel,
                editorInfo = editorInfo,
                logsState = logsState,
                toggleLogsAction = toggleLogsAction,
            )
        }
    }
}

@Composable
fun ScreenEditorContentWithDrawerUi(editorInfo: EditorInfo) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val nav = navigation()
    val drawerInterceptorKey = "INTERCEPTOR_LOGS_DRAWER"

    onActive {
        nav.addOnBackPressedInterceptor(drawerInterceptorKey) {
            if (drawerState.isOpen) {
                drawerState.close()
                true
            } else false
        }
    }

    onDispose {
        nav.removeOnBackPressedInterceptor(drawerInterceptorKey)
    }

    ModalDrawerLayout(
        drawerElevation = 0.dp,
        drawerBackgroundColor = Color.Transparent,
        scrimColor = Color.Transparent,
        drawerState = drawerState,
        drawerContent = { LogsContent() },
    ) {
        ScreenEditorContentUi(
            editorInfo = editorInfo,
            withLogsPanel = false,
            logsState = mutableStateOf(false),
            toggleLogsAction = { if (it) drawerState.open() else drawerState.close() },
        )
    }
}

@Preview("ScreenEditor preview [Light Theme]")
@Composable
private fun ScreenEditorPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ScreenEditorUi(
            editorInfo = EditorInfo(projectExamples[0]),
            logsAsDrawer = true,
        ) {}
    }
}

@Preview("ScreenEditor preview [Dark Theme]")
@Composable
private fun ScreenEditorPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ScreenEditorUi(
            editorInfo = EditorInfo(projectExamples[0]),
            logsAsDrawer = true,
        ) {}
    }
}
