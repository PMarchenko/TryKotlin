package com.pmarchenko.itdroid.pocketkotlin.dialog.input

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.ui.tooling.preview.Preview
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.compose.PocketKotlinTheme
import com.pmarchenko.itdroid.pocketkotlin.dialog.DialogInput
import com.pmarchenko.itdroid.pocketkotlin.preferences.AppThemePreference
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput

/**
 * @author Pavel Marchenko
 */
@Composable
fun DeleteFileDialogInput(
    fileName: String,
    deleteAction: () -> Unit,
    dismissAction: () -> Unit,
) {
    DialogInput(
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
        DeleteFileDialogInput(
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
        DeleteFileDialogInput(
            fileName = "File name",
            deleteAction = {},
            dismissAction = {}
        )
    }
}
