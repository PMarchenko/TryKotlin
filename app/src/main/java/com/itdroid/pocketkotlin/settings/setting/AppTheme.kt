package com.itdroid.pocketkotlin.settings.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.ui.compose.AppRadioButton
import com.itdroid.pocketkotlin.ui.compose.ListSection
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.settings.AppPreferencesViewMolder
import com.itdroid.pocketkotlin.utils.TextInput

/**
 * @author itdroid
 */
@Composable
fun AppTheme() {
    Column {
        val prefs = viewModel<AppPreferencesViewMolder>()
        val selectedTheme by prefs.appThemeObservable
            .observeAsState(prefs.appThemeObservable.value!!) // there is always a default value
        val selectThemeAction: (AppThemePreference) -> Unit = { prefs.appTheme = it }

        AppThemeInput(
            selectedTheme = selectedTheme,
            selectThemeAction = selectThemeAction,
        )
    }
}

@Composable
private fun AppThemeInput(
    selectedTheme: AppThemePreference,
    selectThemeAction: (AppThemePreference) -> Unit,
) {
    Column {
        ListSection(
            text = TextInput(R.string.screen__settings__section__app_theme),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        for (theme in AppThemePreference.values()) {
            AppRadioButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                item = theme,
                selected = theme == selectedTheme,
                onSelected = selectThemeAction,
                text = TextInput(theme.title)
            )
        }
    }
}

@Preview("Settings:AppTheme preview [Light Theme]")
@Composable
private fun AppThemePreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        AppThemeInput(
            selectedTheme = AppThemePreference.Light,
            selectThemeAction = {}
        )
    }
}

@Preview("Settings:AppTheme preview [Dark Theme]")
@Composable
private fun AppThemePreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        AppThemeInput(
            selectedTheme = AppThemePreference.Light,
            selectThemeAction = {}
        )
    }
}
