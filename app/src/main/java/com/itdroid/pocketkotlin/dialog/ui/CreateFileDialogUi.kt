package com.itdroid.pocketkotlin.dialog.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.dialog.DialogUi
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.projects.model.FileType
import com.itdroid.pocketkotlin.ui.compose.AppRadioButton
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.ui.compose.editTextActiveColor
import com.itdroid.pocketkotlin.utils.TextInput
import com.itdroid.pocketkotlin.utils.checkAllMatched

/**
 * @author itdroid
 */
@Composable
fun CreateFileDialogUi(
    fileName: String,
    isValidFileName: Boolean,
    selectedFileType: FileType,

    onNameChangeAction: (String) -> Unit,
    onFileTypeChanged: (FileType) -> Unit,
    addAction: () -> Unit,
    dismissAction: () -> Unit,
    onTextInputStarted: (SoftwareKeyboardController) -> Unit
) {

    DialogUi(
        title = TextInput(R.string.dialog__create_file__title),
        onDismissRequest = dismissAction,

        negativeButtonText = TextInput(R.string.dialog__create_file__negative_button),
        negativeOnClick = dismissAction,

        positiveButtonText = TextInput(R.string.dialog__create_file__positive_button),
        positiveOnClick = addAction,
    ) {
        Column {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = fileName,
                onValueChange = onNameChangeAction,
                activeColor = editTextActiveColor(),
                label = { Text(stringResource(R.string.dialog__create_file__label__name)) },
                isErrorValue = !isValidFileName,
                imeAction = ImeAction.Done,
                onImeActionPerformed = { _, _ -> addAction() },
                onTextInputStarted = onTextInputStarted,
            )

            Spacer(Modifier.size(4.dp))

            for (type in FileType.values()) {
                AppRadioButton(
                    modifier = Modifier.fillMaxWidth(),
                    item = type,
                    selected = type == selectedFileType,
                    text = TextInput(stringResFor(type)),
                    onSelected = { onFileTypeChanged(type) }
                )
            }
        }
    }
}

@StringRes
fun stringResFor(type: FileType) =
    when (type) {
        FileType.File -> R.string.dialog__create_file__label__file
        FileType.Class -> R.string.dialog__create_file__label__class
        FileType.DataClass -> R.string.dialog__create_file__label__data_class
        FileType.Interface -> R.string.dialog__create_file__label__interface
        FileType.Enum -> R.string.dialog__create_file__label__enum
        FileType.Object -> R.string.dialog__create_file__label__object
    }.checkAllMatched

@Preview("CreateFileDialog preview [Light Theme]")
@Composable
private fun CreateFileDialogPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        CreateFileDialogUi(
            fileName = "",
            isValidFileName = true,
            selectedFileType = FileType.File,
            onNameChangeAction = {},
            onFileTypeChanged = {},
            addAction = {},
            dismissAction = {},
            onTextInputStarted = {}
        )
    }
}

@Preview("CreateFileDialog preview [Dark Theme]")
@Composable
private fun CreateFileDialogPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        CreateFileDialogUi(
            fileName = "",
            isValidFileName = true,
            selectedFileType = FileType.File,
            onNameChangeAction = {},
            onFileTypeChanged = {},
            addAction = {},
            dismissAction = {},
                    onTextInputStarted = {}
        )
    }
}
