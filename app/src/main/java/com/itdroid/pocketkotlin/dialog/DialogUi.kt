package com.itdroid.pocketkotlin.dialog

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.ui.compose.AppText
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.utils.TextInput

/**
 * @author itdroid
 */
@Composable
fun DialogUi(
    title: TextInput? = null,
    onDismissRequest: () -> Unit,

    negativeButtonText: TextInput? = null,
    negativeOnClick: () -> Unit = {},

    positiveButtonText: TextInput? = null,
    positiveOnClick: () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = if (title == null) {
            null
        } else {
            {
                AppText(title)
            }
        },
        text = content,
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, end = 24.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {

                if (negativeButtonText != null) {
                    Button(
                        onClick = negativeOnClick,
                    ) {
                        AppText(negativeButtonText)
                    }
                }

                if (positiveButtonText != null) {
                    Button(
                        onClick = positiveOnClick,
                    ) {
                        AppText(positiveButtonText)
                    }
                }
            }
        }
    )
}

@Preview("Dialog preview [Light Theme]")
@Composable
private fun DialogPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        DialogUi(
            title = TextInput(text = "Hello, World!"),
            onDismissRequest = {},
            negativeButtonText = TextInput(text = "Cancel"),
            positiveButtonText = TextInput(text = "Ok")
        ) {
            Text("Hello, World!")
        }
    }
}

@Preview("Dialog preview [Dark Theme]")
@Composable
private fun DialogPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        DialogUi(
            title = TextInput(text = "Hello, World!"),
            onDismissRequest = {},
            negativeButtonText = TextInput(text = "Cancel"),
            positiveButtonText = TextInput(text = "Ok")
        ) {
            Text("Hello, World!")
        }
    }
}