package com.pmarchenko.itdroid.pocketkotlin.editor.configuration

import androidx.compose.foundation.AmbientContentColor
import androidx.compose.foundation.AmbientTextStyle
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.ListItem
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.onActive
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.compose.*
import com.pmarchenko.itdroid.pocketkotlin.compose.state.defaultUiState
import com.pmarchenko.itdroid.pocketkotlin.dialog.CreateFileDialog
import com.pmarchenko.itdroid.pocketkotlin.dialog.DeleteFileDialog
import com.pmarchenko.itdroid.pocketkotlin.dialog.ProjectArgsDialog
import com.pmarchenko.itdroid.pocketkotlin.dialog.dialogs
import com.pmarchenko.itdroid.pocketkotlin.editor.EditorInfo
import com.pmarchenko.itdroid.pocketkotlin.editor.EditorViewModel
import com.pmarchenko.itdroid.pocketkotlin.navigation.ProjectEditorDestination
import com.pmarchenko.itdroid.pocketkotlin.navigation.navigation
import com.pmarchenko.itdroid.pocketkotlin.preferences.AppThemePreference
import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.projects.projectExamples
import com.pmarchenko.itdroid.pocketkotlin.utils.ImageInput
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput
import com.pmarchenko.itdroid.pocketkotlin.utils.tint

/**
 * @author Pavel Marchenko
 */
@Composable
fun ScreenProjectConfiguration(projectId: Long) {
    val vm = viewModel<EditorViewModel>()

    onActive {
        vm.loadProject(projectId)
    }

    val uiState by vm.uiState.observeAsState(defaultUiState())
    val dialog = dialogs()
    val nav = navigation()

    uiState.consume { editorInfo ->
        val changeArgsAction: () -> Unit = { dialog.show(ProjectArgsDialog(editorInfo.project)) }
        val openFileAction: (ProjectFile) -> Unit = { file ->
            nav.navigateTo(ProjectEditorDestination(projectId, file.id), true)
        }
        val deleteFileAction: (ProjectFile) -> Unit = { dialog.show(DeleteFileDialog(it)) }
        val addFileAction: () -> Unit = { dialog.show(CreateFileDialog(editorInfo.project)) }

        AppScreen(
            title = TextInput(
                text = stringResource(R.string.screen__project_configuration__title,
                    editorInfo.project.name)
            )
        ) {
            ScreenProjectConfigurationInput(
                editorInfo = editorInfo,
                changeArgsAction = changeArgsAction,
                openFileAction = openFileAction,
                deleteFileAction = deleteFileAction,
                addFileAction = addFileAction,
            )
        }
    }
}

@Composable
private fun ScreenProjectConfigurationInput(
    editorInfo: EditorInfo,
    changeArgsAction: () -> Unit,
    openFileAction: (ProjectFile) -> Unit,
    deleteFileAction: (ProjectFile) -> Unit,
    addFileAction: () -> Unit,
) {
    ScrollableColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        ProjectArgs(
            project = editorInfo.project,
            changeArgsAction = changeArgsAction
        )

        ProjectFiles(
            project = editorInfo.project,
            openFileAction = openFileAction,
            deleteFileAction = deleteFileAction,
            addFileAction = addFileAction,
        )
    }
}

@Composable
private fun ProjectArgs(
    project: Project,
    changeArgsAction: () -> Unit,
) {
    val subtitleStyle = if (project.args.isEmpty()) {
        AmbientTextStyle.current.copy(fontStyle = FontStyle.Italic)
    } else {
        null
    }
    val subtitle = if (project.args.isEmpty()) {
        TextInput(R.string.screen__project_configuration__label__empty_args)
    } else {
        TextInput(text = project.args)
    }

    TwoLineListItem(
        title = TextInput(R.string.screen__project_configuration__label__args),
        subtitle = subtitle,
        subtitleStyle = subtitleStyle,
        onClick = changeArgsAction
    )

    AppDivider()
}

@Composable
fun ProjectFiles(
    project: Project,
    openFileAction: (ProjectFile) -> Unit,
    deleteFileAction: (ProjectFile) -> Unit,
    addFileAction: () -> Unit,
) {
    AppText(
        modifier = Modifier.padding(16.dp),
        text = TextInput(R.string.screen__project_configuration__label__project_files)
    )
    Divider(Modifier.padding(start = 16.dp))

    for (file in project.files) {
        File(
            file = file,
            openFileAction = openFileAction,
            deleteFileAction = deleteFileAction,
        )

        Divider(Modifier.padding(start = 16.dp))
    }

    ListItem(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { addFileAction() },
        icon = {
            AppImage(
                modifier = Modifier.padding(12.dp),
                image = ImageInput(Icons.Default.Add).tint(AmbientContentColor.current)
            )
        },
        text = {
            AppText(TextInput(R.string.screen__project_configuration__label__add_file))
        }
    )

    Divider(Modifier.padding(start = 16.dp))
}

@Composable
private fun File(
    modifier: Modifier = Modifier,
    file: ProjectFile,
    openFileAction: (ProjectFile) -> Unit,
    deleteFileAction: (ProjectFile) -> Unit,
) {
    ListItem(
        modifier = modifier.clickable { openFileAction(file) },
        trailing = {
            Surface(
                modifier = Modifier.padding(8.dp),
                shape = CircleShape,
                color = Color.Unspecified,
                elevation = 0.dp
            ) {
                AppImage(
                    modifier = Modifier.clickable { deleteFileAction(file) }
                        .padding(12.dp),
                    image = ImageInput(Icons.Default.Delete)
                        .tint(AmbientContentColor.current)
                )
            }
        },
        text = {
            AppText(
                modifier = Modifier.padding(start = 16.dp),
                text = TextInput(text = file.name)
            )
        }
    )
}

@Preview("ScreenLicense preview [Light Theme]")
@Composable
private fun ScreenLicensePreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ScreenProjectConfigurationInput(
            editorInfo = EditorInfo(projectExamples[0]),
            changeArgsAction = {},
            openFileAction = {},
            deleteFileAction = {},
            addFileAction = {}
        )
    }
}

@Preview("ScreenLicense preview [Dark Theme]")
@Composable
private fun ScreenLicensePreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ScreenProjectConfigurationInput(
            editorInfo = EditorInfo(projectExamples[0]),
            changeArgsAction = {},
            openFileAction = {},
            deleteFileAction = {},
            addFileAction = {}
        )
    }
}
