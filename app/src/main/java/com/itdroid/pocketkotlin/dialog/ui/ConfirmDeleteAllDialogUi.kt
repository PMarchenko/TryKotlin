package com.itdroid.pocketkotlin.dialog.ui

import androidx.compose.runtime.Composable
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.dialog.DialogUi
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.ui.compose.AppText
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.utils.TextInput

/**
 * @author itdroid
 */
@Composable
fun ConfirmDeleteAllDialogUi(
    dismissAction: () -> Unit,
    deleteAllAction: () -> Unit,
) {
    DialogUi(
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
        ConfirmDeleteAllDialogUi(
            dismissAction = {},
            deleteAllAction = {}
        )
    }
}

@Preview("ConfirmDeleteAllDialog preview [Dark Theme]")
@Composable
private fun ConfirmDeleteAllDialogPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ConfirmDeleteAllDialogUi(
            dismissAction = {},
            deleteAllAction = {}
        )
    }
}