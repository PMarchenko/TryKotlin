package com.pmarchenko.itdroid.pocketkotlin.dialog

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.pmarchenko.itdroid.pocketkotlin.compose.AppText
import com.pmarchenko.itdroid.pocketkotlin.compose.PocketKotlinTheme
import com.pmarchenko.itdroid.pocketkotlin.preferences.AppThemePreference
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput

/**
 * @author Pavel Marchenko
 */
@Composable
fun DialogInput(
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
                        elevation = 0.dp,
                        backgroundColor = Color.Transparent,
                    ) {
                        AppText(negativeButtonText)
                    }
                }

                if (positiveButtonText != null) {
                    Button(
                        onClick = positiveOnClick,
                        elevation = 0.dp,
                        backgroundColor = Color.Transparent,
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
        DialogInput(
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
        DialogInput(
            title = TextInput(text = "Hello, World!"),
            onDismissRequest = {},
            negativeButtonText = TextInput(text = "Cancel"),
            positiveButtonText = TextInput(text = "Ok")
        ) {
            Text("Hello, World!")
        }
    }
}