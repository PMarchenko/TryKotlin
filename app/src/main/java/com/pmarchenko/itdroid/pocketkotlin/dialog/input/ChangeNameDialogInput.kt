package com.pmarchenko.itdroid.pocketkotlin.dialog.input

import androidx.compose.foundation.Text
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
fun ChangeNameDialogInput(
    isValidProjectName: Boolean,
    newName: String,

    saveAction: () -> Unit,
    nameChangeAction: (String) -> Unit,
    dismissAction: () -> Unit,
    onTextInputStartedAction: (SoftwareKeyboardController) -> Unit,
) {
    DialogInput(
        negativeButtonText = TextInput(R.string.dialog__change_project_name__negative_button),
        negativeOnClick = dismissAction,

        positiveButtonText = TextInput(R.string.dialog__change_project_name__positive_button),
        positiveOnClick = saveAction,

        onDismissRequest = dismissAction,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = newName,
            onValueChange = nameChangeAction,
            activeColor = editTextActiveColor(),
            label = { Text(stringResource(R.string.dialog__change_project_name__label)) },
            isErrorValue = isValidProjectName,
            imeAction = ImeAction.Done,
            onImeActionPerformed = { _, _ -> saveAction() },
            onTextInputStarted = onTextInputStartedAction
        )
    }
}

@Preview("ChangeNameDialog preview [Light Theme]")
@Composable
private fun ChangeNameDialogLightPreview() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ChangeNameDialogInput(
            isValidProjectName = true,
            newName = "",
            saveAction = {},
            nameChangeAction = {},
            dismissAction = {},
            onTextInputStartedAction = {}
        )
    }
}

@Preview("ChangeNameDialog preview [Dark Theme]")
@Composable
private fun ChangeNameDialogDarkPreview() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ChangeNameDialogInput(
            isValidProjectName = true,
            newName = "",
            saveAction = {},
            nameChangeAction = {},
            dismissAction = {},
            onTextInputStartedAction = {}
        )
    }
}
