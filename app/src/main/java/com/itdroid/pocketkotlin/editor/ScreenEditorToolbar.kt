package com.itdroid.pocketkotlin.editor

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.dialog.CreateFileDialog
import com.itdroid.pocketkotlin.dialog.ProjectArgsDialog
import com.itdroid.pocketkotlin.dialog.dialogs
import com.itdroid.pocketkotlin.navigation.ProjectConfigurationDestination
import com.itdroid.pocketkotlin.navigation.navigation
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.projects.model.Project
import com.itdroid.pocketkotlin.projects.model.ProjectFile
import com.itdroid.pocketkotlin.projects.projectExamples
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.ui.compose.PopupMenu
import com.itdroid.pocketkotlin.ui.compose.Toolbar
import com.itdroid.pocketkotlin.ui.compose.ToolbarAction
import com.itdroid.pocketkotlin.utils.ImageInput
import com.itdroid.pocketkotlin.utils.TextInput
import com.itdroid.pocketkotlin.utils.UiAction

/**
 * @author itdroid
 */
@Composable
fun ScreenEditorToolbar(
    editorInfo: EditorInfo,
    logsAsDrawer: Boolean,
    toggleLogsAction: (Boolean) -> Unit,
) {
    val vm = viewModel<EditorViewModel>()
    var showPopup by savedInstanceState { false }
    val nav = navigation()
    val dialog = dialogs()

    ScreenEditorToolbarUi(
        includeLogsTab = logsAsDrawer,
        editorInfo = editorInfo,
        showPopup = showPopup,
        navUpAction = { nav.popCurrentScreen() },
        showPopupAction = { showPopup = true },
        dismissPopupAction = { showPopup = false },
        openConfigAction = { nav.navigateTo(ProjectConfigurationDestination(it.id)) },
        editArgsAction = { dialog.show(ProjectArgsDialog(it)) },
        showLogsAction = { toggleLogsAction(true) },
        selectFileAction = { vm.selectFile(it) },
        addFileAction = { dialog.show(CreateFileDialog(editorInfo.project)) }
    )
}

@Composable
private fun ScreenEditorToolbarUi(
    includeLogsTab: Boolean,
    editorInfo: EditorInfo,
    showPopup: Boolean,

    navUpAction: () -> Unit,
    showPopupAction: () -> Unit,
    dismissPopupAction: () -> Unit,
    openConfigAction: (Project) -> Unit,
    editArgsAction: (Project) -> Unit,
    showLogsAction: () -> Unit,
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
                selectedTabIndex = editorInfo.selectedFileIndex() + if (includeLogsTab) 1 else 0,
                backgroundColor = Color.Transparent,
                edgePadding = 0.dp,
                tabs = {
                    ProjectTabs(
                        includeLogsTab = includeLogsTab,
                        editorInfo = editorInfo,
                        showLogsAction = showLogsAction,
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
    includeLogsTab: Boolean,
    editorInfo: EditorInfo,
    showLogsAction: () -> Unit,
    selectFileAction: (Long) -> Unit,
    addFileAction: () -> Unit,
) {
    if (includeLogsTab) {
        // we have logs content as drawer
        Tab(
            selected = false,
            text = { Text(stringResource(R.string.screen__project_editor__tab__logs)) },
            onClick = { showLogsAction() },
        )
    }

    for (file in editorInfo.project.files) {
        Tab(
            selected = editorInfo.selectedFileId == file.id,
            text = { FileTabContent(file) },
            onClick = { selectFileAction(file.id) }
        )
    }

    Tab(
        selected = false,
        icon = {
            Icon(Icons.Default.Add)
        },
        onClick = addFileAction,
    )
}

@Composable
fun FileTabContent(file: ProjectFile) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(vectorResource(R.drawable.ic__editor__file_icon))
        
        Text(file.name)
    }
}

@Preview("EditorToolbar preview [Light Theme]")
@Composable
private fun ScreenEditorPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ScreenEditorToolbarUi(
            includeLogsTab = true,
            editorInfo = EditorInfo(projectExamples[0]),
            showPopup = true,
            navUpAction = {},
            showPopupAction = {},
            dismissPopupAction = {},
            openConfigAction = {},
            editArgsAction = {},
            showLogsAction = {},
            selectFileAction = {},
            addFileAction = {}
        )
    }
}

@Preview("EditorToolbar preview [Dark Theme]")
@Composable
private fun ScreenEditorPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ScreenEditorToolbarUi(
            includeLogsTab = true,
            editorInfo = EditorInfo(projectExamples[0]),
            showPopup = true,
            navUpAction = {},
            showPopupAction = {},
            dismissPopupAction = {},
            openConfigAction = {},
            editArgsAction = {},
            showLogsAction = {},
            selectFileAction = {},
            addFileAction = {}
        )
    }
}