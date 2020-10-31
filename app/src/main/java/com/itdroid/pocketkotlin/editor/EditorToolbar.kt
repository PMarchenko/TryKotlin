package com.itdroid.pocketkotlin.editor

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.ui.compose.*
import com.itdroid.pocketkotlin.dialog.CreateFileDialog
import com.itdroid.pocketkotlin.dialog.ProjectArgsDialog
import com.itdroid.pocketkotlin.dialog.dialogs
import com.itdroid.pocketkotlin.navigation.ProjectConfigurationDestination
import com.itdroid.pocketkotlin.navigation.navigation
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.projects.model.Project
import com.itdroid.pocketkotlin.projects.projectExamples
import com.itdroid.pocketkotlin.utils.ImageInput
import com.itdroid.pocketkotlin.utils.TextInput
import com.itdroid.pocketkotlin.utils.UiAction
import com.itdroid.pocketkotlin.utils.tint

/**
 * @author itdroid
 */
@Composable
fun EditorToolbar(
    editorInfo: EditorInfo,
    drawerState: DrawerState?,
) {
    val vm = viewModel<EditorViewModel>()
    var showPopup by savedInstanceState { false }
    val nav = navigation()
    val dialog = dialogs()

    EditorToolbarUi(
        editorInfo = editorInfo,
        showPopup = showPopup,
        drawerState = drawerState,
        navUpAction = { nav.popCurrentScreen() },
        showPopupAction = { showPopup = true },
        dismissPopupAction = { showPopup = false },
        openConfigAction = { nav.navigateTo(ProjectConfigurationDestination(it.id)) },
        editArgsAction = { dialog.show(ProjectArgsDialog(it)) },
        openDrawerAction = { drawerState?.open() },
        selectFileAction = { vm.selectFile(it) },
        addFileAction = { dialog.show(CreateFileDialog(editorInfo.project)) }
    )
}

@Composable
private fun EditorToolbarUi(
    editorInfo: EditorInfo,
    showPopup: Boolean,
    drawerState: DrawerState?,

    navUpAction: () -> Unit,
    showPopupAction: () -> Unit,
    dismissPopupAction: () -> Unit,
    openConfigAction: (Project) -> Unit,
    editArgsAction: (Project) -> Unit,
    openDrawerAction: () -> Unit,
    selectFileAction: (Long) -> Unit,
    addFileAction: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Toolbar(
                title = TextInput(text = editorInfo.project.name),
                icon = ImageInput(vector = Icons.Filled.ArrowBack),
                iconOnClick = navUpAction,
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                actions = {
                    ToolbarAction(
                        image = ImageInput(Icons.Default.MoreVert),
                        onClick = showPopupAction
                    )
                }
            )

            ScrollableTabRow(
                selectedTabIndex = editorInfo.project.files.indexOf(editorInfo.selectedFile())
                        + (if (drawerState == null) 0 else 1),
                backgroundColor = Color.Transparent,
                edgePadding = 0.dp,
                tabs = {
                    ProjectTabs(
                        editorInfo = editorInfo,
                        drawerState = drawerState,
                        openDrawerAction = openDrawerAction,
                        selectFileAction = selectFileAction,
                        addFileAction = addFileAction,
                    )
                }
            )
        }

        if (showPopup) {
            PopupMenu(
                data = editorInfo.project,
                items = listOf(
                    UiAction(
                        title = TextInput(R.string.screen__project_editor__overflow_menu__manage_config),
                        onClick = openConfigAction
                    ),
                    UiAction(
                        title = if (editorInfo.project.args.isNotEmpty()) {
                            TextInput(
                                text = stringResource(
                                    R.string.screen__project_editor__overflow_menu__args,
                                    editorInfo.project.args
                                )
                            )
                        } else {
                            TextInput(R.string.screen__project_editor__overflow_menu__args_empty)
                        },
                        onClick = editArgsAction
                    ),
                ),
                onDismissRequest = dismissPopupAction
            )
        }
    }
}

@Composable
private fun ProjectTabs(
    editorInfo: EditorInfo,
    drawerState: DrawerState?,
    openDrawerAction: () -> Unit,
    selectFileAction: (Long) -> Unit,
    addFileAction: () -> Unit,
) {
    if (drawerState != null) {
        // we have logs content as drawer
        Tab(
            selected = false,
            text = { Text(stringResource(R.string.screen__project_editor__tab__logs)) },
            onClick = { openDrawerAction() },
        )
    }

    for (file in editorInfo.project.files) {
        Tab(
            selected = editorInfo.selectedFileId == file.id,
            text = { Text(file.name) },
            onClick = { selectFileAction(file.id) }
        )
    }

    Tab(
        selected = false,
        icon = {
            AppImage(
                ImageInput(Icons.Default.Add).tint(MaterialTheme.colors.onPrimary)
            )
        },
        onClick = addFileAction,
    )
}

@Preview("EditorToolbar preview [Light Theme]")
@Composable
private fun ScreenEditorPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        EditorToolbarUi(
            editorInfo = EditorInfo(projectExamples[0]),
            showPopup = true,
            drawerState = rememberDrawerState(DrawerValue.Open),
            navUpAction = {},
            showPopupAction = {},
            dismissPopupAction = {},
            openConfigAction = {},
            editArgsAction = {},
            openDrawerAction = {},
            selectFileAction = {},
            addFileAction = {}
        )
    }
}

@Preview("EditorToolbar preview [Dark Theme]")
@Composable
private fun ScreenEditorPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        EditorToolbarUi(
            editorInfo = EditorInfo(projectExamples[0]),
            showPopup = true,
            drawerState = rememberDrawerState(DrawerValue.Open),
            navUpAction = {},
            showPopupAction = {},
            dismissPopupAction = {},
            openConfigAction = {},
            editArgsAction = {},
            openDrawerAction = {},
            selectFileAction = {},
            addFileAction = {}
        )
    }
}