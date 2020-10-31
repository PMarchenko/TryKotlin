package com.itdroid.pocketkotlin.dialog.ui

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.dialog.DialogUi
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.ui.compose.AppCheckBox
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.ui.compose.editTextActiveColor
import com.itdroid.pocketkotlin.utils.TextInput

/**
 * @author itdroid
 */
@Composable
fun AddNewProjectDialogUi(
    projectNameState: MutableState<String>,
    includeMainState: MutableState<Boolean>,
    isValidState: MutableState<Boolean>,

    nameChangeAction: (String) -> Unit,
    includeMainChangeAction: (Boolean) -> Unit,
    addProjectAction: () -> Unit,
    dismissAction: () -> Unit,
    onTextInputStartedAction: (SoftwareKeyboardController) -> Unit,
) {
    DialogUi(
        title = TextInput(R.string.dialog__add_project__title),
        onDismissRequest = dismissAction,

        negativeButtonText = TextInput(R.string.dialog__add_project__negative_button),
        negativeOnClick = dismissAction,

        positiveButtonText = TextInput(R.string.dialog__add_project__positive_button),
        positiveOnClick = addProjectAction,
    ) {
        Column {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = projectNameState.value,
                onValueChange = nameChangeAction,
                activeColor = editTextActiveColor(),
                label = { Text(stringResource(R.string.dialog__add_project__label)) },
                isErrorValue = !isValidState.value,
                imeAction = ImeAction.Done,
                onImeActionPerformed = { _, _ -> addProjectAction() },
                onTextInputStarted = onTextInputStartedAction
            )

            AppCheckBox(
                checked = includeMainState.value,
                text = TextInput(R.string.dialog__add_project__include_main),
                onCheckedChange = includeMainChangeAction
            )
        }
    }
}

@Preview("AddNewProjectDialog preview [Light Theme]")
@Composable
private fun AddNewProjectDialogPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        AddNewProjectDialogUi(
            projectNameState = mutableStateOf("New Project"),
            includeMainState = mutableStateOf(true),
            isValidState = mutableStateOf(true),
            nameChangeAction = {},
            includeMainChangeAction = {},
            addProjectAction = {},
            dismissAction = {},
            onTextInputStartedAction = {}
        )
    }
}

@Preview("AddNewProjectDialog preview [Dark Theme]")
@Composable
private fun LightPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        AddNewProjectDialogUi(
            projectNameState = mutableStateOf("New Project"),
            includeMainState = mutableStateOf(true),
            isValidState = mutableStateOf(true),
            nameChangeAction = {},
            includeMainChangeAction = {},
            addProjectAction = {},
            dismissAction = {},
            onTextInputStartedAction = {}
        )
    }
}
