package com.pmarchenko.itdroid.pocketkotlin.dialog.input

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.ui.tooling.preview.Preview
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.dialog.DialogInput
import com.pmarchenko.itdroid.pocketkotlin.preferences.AppThemePreference
import com.pmarchenko.itdroid.pocketkotlin.compose.PocketKotlinTheme
import com.pmarchenko.itdroid.pocketkotlin.compose.editTextActiveColor
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput

/**
 * @author Pavel Marchenko
 */
@Composable
fun ProjectArgsDialogInput(
    args: String,

    onArgsChangeAction: (String) -> Unit,
    saveAction: () -> Unit,
    dismissAction: () -> Unit,
    onTextInputStarted: (SoftwareKeyboardController) -> Unit,
) {
    DialogInput(
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
            activeColor = editTextActiveColor(),
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
        ProjectArgsDialogInput(
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
        ProjectArgsDialogInput(
            args = "Some args",
            onArgsChangeAction = {},
            saveAction = {},
            dismissAction = {},
            onTextInputStarted = {}
        )
    }
}
