package com.pmarchenko.itdroid.pocketkotlin.dialog.input

import androidx.compose.runtime.Composable
import androidx.ui.tooling.preview.Preview
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.dialog.DialogInput
import com.pmarchenko.itdroid.pocketkotlin.preferences.AppThemePreference
import com.pmarchenko.itdroid.pocketkotlin.compose.AppText
import com.pmarchenko.itdroid.pocketkotlin.compose.PocketKotlinTheme
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput

/**
 * @author Pavel Marchenko
 */
@Composable
fun ConfirmDeleteAllDialogInput(
    dismissAction: () -> Unit,
    deleteAllAction: () -> Unit,
) {
    DialogInput(
        title = TextInput(R.string.dialog__delete_all__title),
        onDismissRequest = dismissAction,
        negativeButtonText = TextInput(R.string.dialog__delete_all__negative_button),
        negativeOnClick = dismissAction,

        positiveButtonText = TextInput(R.string.dialog__delete_all__positive_button),
        positiveOnClick = {
            deleteAllAction()
            dismissAction()
        },
    ) {
        AppText(TextInput(R.string.dialog__delete_all__description))
    }
}

@Preview("ConfirmDeleteAllDialog preview [Light Theme]")
@Composable
private fun ConfirmDeleteAllDialogPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ConfirmDeleteAllDialogInput(
            dismissAction = {},
            deleteAllAction = {}
        )
    }
}

@Preview("ConfirmDeleteAllDialog preview [Dark Theme]")
@Composable
private fun ConfirmDeleteAllDialogPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ConfirmDeleteAllDialogInput(
            dismissAction = {},
            deleteAllAction = {}
        )
    }
}