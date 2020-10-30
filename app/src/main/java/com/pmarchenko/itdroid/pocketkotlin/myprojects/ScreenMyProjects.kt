package com.pmarchenko.itdroid.pocketkotlin.myprojects

import androidx.compose.foundation.Icon
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.dialog.AddNewProjectDialog
import com.pmarchenko.itdroid.pocketkotlin.dialog.ChangeNameDialog
import com.pmarchenko.itdroid.pocketkotlin.dialog.dialogs
import com.pmarchenko.itdroid.pocketkotlin.navigation.ProjectEditorDestination
import com.pmarchenko.itdroid.pocketkotlin.navigation.navigation
import com.pmarchenko.itdroid.pocketkotlin.preferences.AppThemePreference
import com.pmarchenko.itdroid.pocketkotlin.project.projects
import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project
import com.pmarchenko.itdroid.pocketkotlin.projects.projectExamples
import com.pmarchenko.itdroid.pocketkotlin.compose.Empty
import com.pmarchenko.itdroid.pocketkotlin.compose.PocketKotlinTheme
import com.pmarchenko.itdroid.pocketkotlin.compose.ProjectsGrid
import com.pmarchenko.itdroid.pocketkotlin.compose.state.UiState
import com.pmarchenko.itdroid.pocketkotlin.compose.state.defaultUiState
import com.pmarchenko.itdroid.pocketkotlin.compose.state.loadingUiState
import com.pmarchenko.itdroid.pocketkotlin.navigation.ProjectConfigurationDestination
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput
import com.pmarchenko.itdroid.pocketkotlin.utils.UiAction

/**
 * @author Pavel Marchenko
 */
@Composable
fun ScreenMyProjects() {
    val vm = viewModel<MyProjectsViewModel>()
    val projectsVm = projects()
    val uiState by vm.state.observeAsState(loadingUiState())
    val dialog = dialogs()
    val nav = navigation()

    val openConfigAction: (Project) -> Unit = { project ->
        nav.navigateTo(ProjectConfigurationDestination(project.id))
    }
    val openProjectAction: (Project) -> Unit = { project ->
        nav.navigateTo(ProjectEditorDestination(project.id))
    }
    val addProjectAction = { dialog.show(AddNewProjectDialog) }
    val changeNameAction: (Project) -> Unit = { dialog.show(ChangeNameDialog(it)) }
    val deleteProjectAction: (Project) -> Unit = { project -> projectsVm.delete(project) }

    ScreenMyProjectsInput(
        uiState = uiState,
        openConfigAction = openConfigAction,
        openProjectAction = openProjectAction,
        addProjectAction = addProjectAction,
        changeNameAction = changeNameAction,
        deleteProjectAction = deleteProjectAction
    )
}

@Composable
private fun ScreenMyProjectsInput(
    uiState: UiState<List<Project>>,
    openConfigAction: (Project) -> Unit,
    openProjectAction: (Project) -> Unit,
    addProjectAction: () -> Unit,
    changeNameAction: (Project) -> Unit,
    deleteProjectAction: (Project) -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        uiState.consume { projects ->
            if (projects.isEmpty()) {
                Empty(R.string.screen__my_projects__label__empty)
            } else {
                ProjectsInput(
                    projects = projects,
                    openConfigAction = openConfigAction,
                    openProjectAction = openProjectAction,
                    changeNameAction = changeNameAction,
                    deleteProjectAction = deleteProjectAction
                )
            }
        }

        Fab(
            modifier = Modifier.Companion
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 16.dp),
            onClick = addProjectAction
        )
    }
}

@Composable
private fun ProjectsInput(
    projects: List<Project>,
    openConfigAction: (Project) -> Unit,
    openProjectAction: (Project) -> Unit,
    changeNameAction: (Project) -> Unit,
    deleteProjectAction: (Project) -> Unit,
) {
    val popupItems =
        prepareMenuItems(
            openConfigAction = openConfigAction,
            changeNameAction = changeNameAction,
            deleteAction = deleteProjectAction,
        )

    ProjectsGrid(
        projects = projects,
        popupItems = popupItems,
        onItemClick = openProjectAction)
}

@Composable
private fun Fab(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        icon = { Icon(Icons.Default.Add) },
        elevation = 4.dp //TODO investigate why it crashes with IllegalStateException with default elevation
    )
}

private fun prepareMenuItems(
    openConfigAction: (Project) -> Unit,
    changeNameAction: (Project) -> Unit,
    deleteAction: (Project) -> Unit,
) = listOf(
    UiAction(
        title = TextInput(R.string.screen__my_projects__popup_menu__my_projects__config),
        onClick = openConfigAction
    ),
    UiAction(
        title = TextInput(R.string.screen__my_projects__popup_menu__my_projects__change_name),
        onClick = changeNameAction
    ),
    UiAction(
        title = TextInput(R.string.screen__my_projects__popup_menu__my_projects__delete),
        onClick = deleteAction
    ),
)


@Preview("ScreenMyProjects preview [Light Theme]")
@Composable
private fun MyProjectsScreenPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ScreenMyProjectsInput(
            uiState = defaultUiState(projectExamples),
            openConfigAction = {},
            openProjectAction = {},
            addProjectAction = {},
            changeNameAction = {},
            deleteProjectAction = {}
        )
    }
}

@Preview("ScreenMyProjects preview [Dark Theme]")
@Composable
private fun MyProjectsScreenPreview() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ScreenMyProjectsInput(
            uiState = defaultUiState(projectExamples),
            openConfigAction = {},
            openProjectAction = {},
            addProjectAction = {},
            changeNameAction = {},
            deleteProjectAction = {}
        )
    }
}
