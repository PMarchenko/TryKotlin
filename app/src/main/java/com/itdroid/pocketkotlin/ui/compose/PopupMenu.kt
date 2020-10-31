package com.itdroid.pocketkotlin.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ListItem
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.utils.TextInput
import com.itdroid.pocketkotlin.utils.UiAction

/**
 * @author itdroid
 */

val defaultPopupWidth = 204.dp

@Composable
fun <T> PopupMenu(
    data: T,
    items: List<UiAction<T>>,
    onDismissRequest: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Popup(
            alignment = Alignment.TopEnd,
            isFocusable = true,
            onDismissRequest = onDismissRequest
        ) {
            Surface(
                modifier = Modifier
                    .width(defaultPopupWidth)
                    .padding(top = 8.dp, end = 8.dp),
                shape = RoundedCornerShape(4.dp),
                elevation = 8.dp,
            ) {
                Column {
                    for (action in items) {
                        ListItem(
                            modifier = Modifier
                                .clickable {
                                    action.onClick(data)
                                    onDismissRequest()
                                }
                                .fillMaxWidth(),
                            text = {
                                AppText(text = action.title)
                            }
                        )
                    }
                }
            }
        }
    }
}


@Preview("PopupMenu preview [Light Theme]")
@Composable
private fun PopupMenuPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        PopupMenu(Any(), previewActions) {}
    }
}

@Preview("PopupMenu preview [Dark Theme]")
@Composable
private fun PopupMenuScreenDarkThemePreview() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        PopupMenu(Any(), previewActions) {}
    }
}

private val previewActions = listOf<UiAction<Any>>(
    UiAction(
        title = TextInput(text = "Item with icon"),
        onClick = {}
    ),
    UiAction(
        title = TextInput(text = "Item without icon"),
        onClick = {}
    )
)
