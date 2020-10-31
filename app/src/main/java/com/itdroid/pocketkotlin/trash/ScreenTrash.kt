package com.itdroid.pocketkotlin.trash

import android.text.format.DateUtils
import androidx.compose.foundation.AmbientContentColor
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.ui.compose.Empty
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.ui.compose.ProjectPreview
import com.itdroid.pocketkotlin.ui.compose.ProjectsGrid
import com.itdroid.pocketkotlin.ui.compose.state.UiState
import com.itdroid.pocketkotlin.ui.compose.state.defaultUiState
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.project.projects
import com.itdroid.pocketkotlin.projects.model.Project
import com.itdroid.pocketkotlin.projects.projectExamples
import com.itdroid.pocketkotlin.utils.TextInput
import com.itdroid.pocketkotlin.utils.UiAction

/**
 * @author itdroid
 */
@Composable
fun ScreenTrash() {
    Box(Modifier.fillMaxSize()) {
        val uiState by viewModel<TrashViewModel>().uiState.observeAsState(defaultUiState())
        val projects = projects()
        val restoreProjectAction: (Project) -> Unit = { projects.restore(it) }
        val deleteProjectAction: (Project) -> Unit = { projects.deleteForever(it) }
        ScreenTrashInput(
            uiState = uiState,
            restoreProjectAction = restoreProjectAction,
            deleteProjectAction = deleteProjectAction
        )
    }
}

@Composable
private fun ScreenTrashInput(
    uiState: UiState<List<Project>>,
    restoreProjectAction: (Project) -> Unit,
    deleteProjectAction: (Project) -> Unit,
) {

    uiState.consume { projects ->
        if (projects.isEmpty()) Empty(R.string.screen__trash_bin__empty)
        else ProjectsGrid(
            projects = projects,
            popupActions = prepareMenuItems(
                restoreProjectAction = restoreProjectAction,
                deleteProjectAction = deleteProjectAction
            ),
            projectPreview = { DeletedProjectPreview(it) },
            projectFooter = { DeletedProjectFooter(it) },
        )
    }
}

@Composable
private fun DeletedProjectPreview(project: Project) {
    Surface(
        contentColor = deletedContentColder().copy(alpha = 0.4f),
    ) {
        Box(
            modifier = Modifier
                .background(
                    MaterialTheme.colors.background,
                    RoundedCornerShape(topLeft = 4.dp, topRight = 4.dp)
                )
                .fillMaxWidth()
                .height(160.dp)
                .padding(8.dp)
        ) {
            ProjectPreview(project)

            Image(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(124.dp),
                colorFilter = ColorFilter.tint(deletedContentColder().copy(alpha = 0.1f)),
                asset = Icons.Outlined.Delete
            )
        }
    }
}

@Composable
fun DeletedProjectFooter(project: Project) {
    Surface(
        contentColor = deletedContentColder().copy(alpha = 0.4f),
    ) {
        Column {
            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.h5,
                text = project.name
            )

            val dateDeleted = DateUtils.formatDateTime(
                ContextAmbient.current,
                project.dateSoftDeleted,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_TIME
            )

            Text(
                modifier = Modifier.padding(horizontal = 8.dp),
                style = MaterialTheme.typography.caption,
                text = stringResource(R.string.screen__trash_bin__date_deleted_fmt, dateDeleted),
            )
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}

private fun prepareMenuItems(
    restoreProjectAction: (Project) -> Unit,
    deleteProjectAction: (Project) -> Unit,
) = listOf(
    UiAction(
        title = TextInput(R.string.screen__trash_bin__popup_menu__trash__restore),
        onClick = restoreProjectAction
    ),
    UiAction(
        title = TextInput(R.string.screen__trash_bin__popup_menu__trash__delete_forever),
        onClick = deleteProjectAction
    ),
)

@Composable
private fun deletedContentColder() = AmbientContentColor.current

@Preview("ScreenTrash preview [Light Theme]")
@Composable
private fun ScreenTrashPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ScreenTrashInput(
            uiState = UiState(projectExamples),
            restoreProjectAction = {},
            deleteProjectAction = {},
        )
    }
}

@Preview("ScreenTrash preview [Dark Theme]")
@Composable
private fun ScreenTrashPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ScreenTrashInput(
            uiState = UiState(projectExamples),
            restoreProjectAction = {},
            deleteProjectAction = {},
        )
    }
}
