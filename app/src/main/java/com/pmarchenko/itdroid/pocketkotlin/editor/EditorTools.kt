package com.pmarchenko.itdroid.pocketkotlin.editor

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.compose.AppCheckBox
import com.pmarchenko.itdroid.pocketkotlin.compose.AppDivider
import com.pmarchenko.itdroid.pocketkotlin.compose.PocketKotlinTheme
import com.pmarchenko.itdroid.pocketkotlin.preferences.AppThemePreference
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput

/**
 * @author Pavel Marchenko
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