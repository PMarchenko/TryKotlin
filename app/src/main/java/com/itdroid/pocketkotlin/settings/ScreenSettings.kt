package com.itdroid.pocketkotlin.settings

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.settings.setting.About
import com.itdroid.pocketkotlin.settings.setting.AppTheme
import com.itdroid.pocketkotlin.settings.setting.TrashBin
import com.itdroid.pocketkotlin.ui.compose.AppDivider
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme

/**
 * @author itdroid
 */
@Composable
fun ScreenSettings() {
    ScrollableColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTheme()
        AppDivider()
        TrashBin()
        AppDivider()
        About()
    }
}

@Preview("SettingsScreen preview [Light Theme]")
@Composable
private fun SettingsScreenPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ScreenSettings()
    }
}

@Preview("SettingsScreen preview [Dark Theme]")
@Composable
private fun SettingsScreenPreview() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ScreenSettings()
    }
}
