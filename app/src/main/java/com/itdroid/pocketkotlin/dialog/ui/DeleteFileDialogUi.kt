package com.itdroid.pocketkotlin.dialog.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.dialog.DialogUi
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.utils.TextInput

/**
 * @author itdroid
 */
@Composable
fun DeleteFileDialogUi(
    fileName: String,
    deleteAction: () -> Unit,
    dismissAction: () -> Unit,
) {
    DialogUi(
        title = TextInput(text = stringResource(R.string.dialog__delete_file__title, fileName)),
        onDismissRequest = dismissAction,

        negativeButtonText = TextInput(R.string.dialog__delete_file__negative_button),
        negativeOnClick = dismissAction,

        positiveButtonText = TextInput(R.string.dialog__delete_file__positive_button),
        positiveOnClick = deleteAction,
    )
}

@Preview("DeleteFileDialog preview [Light Theme]")
@Composable
private fun DialogDeleteFilePreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        DeleteFileDialogUi(
            fileName = "File name",
            deleteAction = {},
            dismissAction = {}
        )
    }
}

@Preview("DeleteFileDialog preview [Dark Theme]")
@Composable
private fun DialogDeleteFilePreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        DeleteFileDialogUi(
            fileName = "File name",
            deleteAction = {},
            dismissAction = {}
        )
    }
}
