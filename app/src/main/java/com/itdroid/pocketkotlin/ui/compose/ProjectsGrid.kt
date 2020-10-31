package com.itdroid.pocketkotlin.ui.compose

import android.text.format.DateUtils
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AmbientElevationOverlay
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.projects.model.Project
import com.itdroid.pocketkotlin.utils.UiAction
import com.itdroid.pocketkotlin.utils.takeIfOr

/**
 * @author itdroid
 */
@Composable
fun ProjectsGrid(
    projects: List<Project>,
    popupItems: List<UiAction<Project>>,
    projectPreview: @Composable (ColumnScope.(Project) -> Unit)? = null,
    projectFooter: @Composable (ColumnScope.(Project) -> Unit)? = null,
    onItemClick: ((Project) -> Unit)? = null,
) {
    LazyGridFor(
        projects,
        integerResource(R.integer.projectsListGridSize),
    ) { project ->
        ProjectItem(
            project = project,
            popupActions = popupItems,
            projectPreview = projectPreview,
            projectFooter = projectFooter,
            onClick = onItemClick?.let { { onItemClick(project) } }
        )
    }
}

@Composable
fun ProjectItem(
    project: Project,
    popupActions: List<UiAction<Project>>,
    projectPreview: @Composable (ColumnScope.(Project) -> Unit)? = null,
    projectFooter: @Composable (ColumnScope.(Project) -> Unit)? = null,
    onClick: ((Project) -> Unit)? = null,
) {
    Card(
        elevation = 2.dp,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        val modifier = onClick?.let { Modifier.clickable { onClick(project) } } ?: Modifier
        Box(modifier) {
            Column(Modifier.padding(8.dp)) {
                if (projectPreview == null) {
                    ProjectPreview(project)
                } else {
                    projectPreview(project)
                }
                if (projectFooter == null) {
                    ProjectFooter(project)
                } else {
                    projectFooter(project)
                }
            }
            var showPopup by remember { mutableStateOf(false) }
            Surface(
                color = AmbientElevationOverlay
                    .current
                    ?.apply(color = MaterialTheme.colors.surface, elevation = 2.dp)
                    ?: MaterialTheme.colors.surface,
                shape = RoundedCornerShape(bottomLeft = 4.dp),
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    modifier = Modifier
                        .clickable { showPopup = true }
                        .padding(8.dp),
                    asset = Icons.Default.MoreVert
                )
            }
            if (showPopup) {
                PopupMenu(project, popupActions) {
                    showPopup = false
                }
            }
        }
    }
}

@Composable
fun ProjectPreview(project: Project) {
    val program: String? = if (project.files.isEmpty()) null else project.files[0].program
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
        when {
            program == null -> Text(
                text = stringResource(R.string.screen__my_projects__label__no_files),
                style = MaterialTheme.typography.caption,
                modifier = Modifier.align(Alignment.Center)
            )
            program.isEmpty() -> Text(
                text = stringResource(R.string.screen__my_projects__label__empty_program),
                style = MaterialTheme.typography.caption,
                modifier = Modifier.align(Alignment.Center)
            )
            else -> Text(
                text = program,
                style = MaterialTheme.typography.body1,
            )
        }
    }
}

@Composable
fun ProjectFooter(project: Project) {
    Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.h5,
        text = project.name
    )

    Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        style = MaterialTheme.typography.caption,
        text = DateUtils.formatDateTime(
            ContextAmbient.current,
            project.dateModified.takeIfOr(project.dateModified) { it > 0 },
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR or DateUtils.FORMAT_SHOW_TIME
        ),
    )

    Spacer(modifier = Modifier.height(4.dp))
}