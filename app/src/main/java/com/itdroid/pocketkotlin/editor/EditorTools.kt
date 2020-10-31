package com.itdroid.pocketkotlin.editor

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.ui.compose.AppCheckBox
import com.itdroid.pocketkotlin.ui.compose.AppDivider
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.utils.TextInput

/**
 * @author itdroid
 */

@Composable
fun EditorTools(
    logsVisible: Boolean,
    logsVisibilityAction: (Boolean) -> Unit,
) {
    AppDivider()
    Row(modifier = Modifier.fillMaxWidth()) {
        AppCheckBox(
            modifier = Modifier.padding(4.dp),
            text = TextInput(R.string.editor__tools__logs),
            checked = logsVisible,
            onCheckedChange = logsVisibilityAction
        )
    }
}

@Preview("EditorTools preview [Light Theme]")
@Composable
private fun EditorToolsPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        EditorTools(
            logsVisible = true,
            logsVisibilityAction = {},
        )
    }
}

@Preview("EditorTools preview [Dark Theme]")
@Composable
private fun EditorToolsPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        EditorTools(
            logsVisible = true,
            logsVisibilityAction = {},
        )
    }
}