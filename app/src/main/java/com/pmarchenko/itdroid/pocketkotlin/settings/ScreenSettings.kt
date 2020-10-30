package com.pmarchenko.itdroid.pocketkotlin.settings

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.ui.tooling.preview.Preview
import com.pmarchenko.itdroid.pocketkotlin.preferences.AppThemePreference
import com.pmarchenko.itdroid.pocketkotlin.settings.setting.About
import com.pmarchenko.itdroid.pocketkotlin.settings.setting.AppTheme
import com.pmarchenko.itdroid.pocketkotlin.settings.setting.TrashBin
import com.pmarchenko.itdroid.pocketkotlin.compose.AppDivider
import com.pmarchenko.itdroid.pocketkotlin.compose.PocketKotlinTheme

/**
 * @author Pavel Marchenko
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