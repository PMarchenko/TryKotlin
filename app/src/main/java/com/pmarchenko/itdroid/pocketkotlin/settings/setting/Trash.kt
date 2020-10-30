package com.pmarchenko.itdroid.pocketkotlin.settings.setting

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.compose.PocketKotlinTheme
import com.pmarchenko.itdroid.pocketkotlin.compose.TwoLineListItem
import com.pmarchenko.itdroid.pocketkotlin.compose.state.defaultUiState
import com.pmarchenko.itdroid.pocketkotlin.dialog.ConfirmDeleteAllDialog
import com.pmarchenko.itdroid.pocketkotlin.dialog.dialogs
import com.pmarchenko.itdroid.pocketkotlin.preferences.AppThemePreference
import com.pmarchenko.itdroid.pocketkotlin.trash.TrashViewModel
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput

/**
 * @author Pavel Marchenko
 */
@Composable
fun TrashBin() {
    val vm = viewModel<TrashViewModel>()
    val uiState by vm.uiState.observeAsState(defaultUiState())
    val dialog = dialogs()

    val projectsInTrash = uiState.data?.size ?: 0
    val emptyTrashBinAction =
        if (projectsInTrash > 0) {
            { dialog.show(ConfirmDeleteAllDialog) }
        } else null

    TrashBinInput(
        projectsInTrash = projectsInTrash,
        emptyTrashBinAction = emptyTrashBinAction
    )
}

@Composable
private fun TrashBinInput(
    projectsInTrash: Int,
    emptyTrashBinAction: (() -> Unit)?,
) {
    val subtitle = ContextAmbient.current.resources.getQuantityString(
        R.plurals.screen__settings__desc__trash,
        projectsInTrash,
        projectsInTrash
    )
    TwoLineListItem(
        modifier = Modifier.fillMaxWidth(),
        title = TextInput(R.string.screen__settings__value__trash),
        subtitle = TextInput(text = subtitle),
        onClick = emptyTrashBinAction
    )
}

@Preview("Settings:TrashBin(empty) preview [Light Theme]")
@Composable
private fun TrashBinEmptyPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        TrashBinInput(0) {}
    }
}

@Preview("Settings:TrashBin(empty) preview [Dark Theme]")
@Composable
private fun TrashBinEmptyPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        TrashBinInput(0) {}
    }
}

@Preview("Settings:TrashBin(full) preview [Light Theme]")
@Composable
private fun TrashBinFullPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        TrashBinInput(Int.MAX_VALUE) {}
    }
}

@Preview("Settings:TrashBin(full) preview [Dark Theme]")
@Composable
private fun TrashBinFullPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        TrashBinInput(Int.MAX_VALUE) {}
    }
}