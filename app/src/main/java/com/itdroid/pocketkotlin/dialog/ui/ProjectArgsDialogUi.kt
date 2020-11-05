package com.itdroid.pocketkotlin.dialog.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.dialog.DialogUi
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.ui.compose.editTextActiveColor
import com.itdroid.pocketkotlin.utils.TextInput

/**
 * @author itdroid
 */
@Composable
fun ProjectArgsDialogUi(
    args: String,

    onArgsChangeAction: (String) -> Unit,
    saveAction: () -> Unit,
    dismissAction: () -> Unit,
    onTextInputStarted: (SoftwareKeyboardController) -> Unit,
) {
    DialogUi(
        title = TextInput(R.string.dialog__command_line_args__title),
        onDismissRequest = dismissAction,

        negativeButtonText = TextInput(R.string.dialog__command_line_args__negative_button),
        negativeOnClick = dismissAction,

        positiveButtonText = TextInput(R.string.dialog__command_line_args__positive_button),
        positiveOnClick = saveAction,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = args,
            onValueChange = onArgsChangeAction,
            activeColor = MaterialTheme.colors.editTextActiveColor,
            label = { Text(stringResource(R.string.dialog__command_line_args__hint)) },
            imeAction = ImeAction.Done,
            onImeActionPerformed = { _, _ -> saveAction() },
            onTextInputStarted = onTextInputStarted
        )
    }
}

@Preview("DialogProjectArgs preview [Light Theme]")
@Composable
private fun DialogDeleteFileLightPreview() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ProjectArgsDialogUi(
            args = "Some args",
            onArgsChangeAction = {},
            saveAction = {},
            dismissAction = {},
            onTextInputStarted = {}
        )
    }
}

@Preview("DialogProjectArgs preview [Dark Theme]")
@Composable
private fun DialogDeleteFileDarkPreview() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ProjectArgsDialogUi(
            args = "Some args",
            onArgsChangeAction = {},
            saveAction = {},
            dismissAction = {},
            onTextInputStarted = {}
        )
    }
}
