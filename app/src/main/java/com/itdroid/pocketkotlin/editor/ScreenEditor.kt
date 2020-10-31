package com.itdroid.pocketkotlin.editor

import androidx.compose.foundation.Icon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.onActive
import androidx.compose.runtime.onDispose
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.ui.compose.AppScreen
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.ui.compose.state.defaultUiState
import com.itdroid.pocketkotlin.navigation.navigation
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.projects.projectExamples

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

    val hasDrawer = booleanResource(id = R.bool.editor__with_drawer)
    val drawerState =
        if (hasDrawer) {
            val state = rememberDrawerState(initialValue = DrawerValue.Closed)
            val nav = navigation()
            val drawerInterceptorKey = "INTERCEPTOR_LOGS_DRAWER"

            onActive {
                nav.addOnBackPressedInterceptor(drawerInterceptorKey) {
                    if (state.isOpen) {
                        state.close()
                        true
                    } else false
                }
            }

            onDispose {
                nav.removeOnBackPressedInterceptor(drawerInterceptorKey)
            }
            state
        } else null

    val uiState by vm.uiState.observeAsState(defaultUiState())
    uiState.consume { editorInfo ->
        ScreenEditorUi(
            drawerState = drawerState,
            editorInfo = editorInfo,
            executeProjectAction = { vm.executeProject(editorInfo.project) },
        )
    }
}

@Composable
private fun ScreenEditorUi(
    drawerState: DrawerState?,
    editorInfo: EditorInfo,
    executeProjectAction: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (drawerState == null) {
            Content(editorInfo, null)
        } else {
            ModalDrawerLayout(
                drawerElevation = 0.dp,
                drawerBackgroundColor = Color.Transparent,
                drawerState = drawerState,
                drawerContent = { LogsContent() },
            ) {
                Content(editorInfo, drawerState)
            }
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
private fun Content(
    uiState: EditorInfo,
    drawerState: DrawerState?,
) {
    AppScreen(
        toolbar = { EditorToolbar(uiState, drawerState) },
    ) {
        Box {
            EditorContent(uiState, drawerState != null)
        }
    }
}

@Preview("ScreenEditor preview [Light Theme]")
@Composable
private fun ScreenEditorPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ScreenEditorUi(
            drawerState = rememberDrawerState(DrawerValue.Open),
            editorInfo = EditorInfo(projectExamples[0]),
            executeProjectAction = {}
        )
    }
}

@Preview("ScreenEditor preview [Dark Theme]")
@Composable
private fun ScreenEditorPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ScreenEditorUi(
            drawerState = rememberDrawerState(DrawerValue.Open),
            editorInfo = EditorInfo(projectExamples[0]),
            executeProjectAction = {}
        )
    }
}
