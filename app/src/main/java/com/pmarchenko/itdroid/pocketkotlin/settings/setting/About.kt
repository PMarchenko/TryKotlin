package com.pmarchenko.itdroid.pocketkotlin.settings.setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.pmarchenko.itdroid.pocketkotlin.BuildConfig
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.dialog.EasterEggDialog
import com.pmarchenko.itdroid.pocketkotlin.dialog.dialogs
import com.pmarchenko.itdroid.pocketkotlin.navigation.LicensesDestination
import com.pmarchenko.itdroid.pocketkotlin.navigation.navigation
import com.pmarchenko.itdroid.pocketkotlin.preferences.AppThemePreference
import com.pmarchenko.itdroid.pocketkotlin.settings.AppPreferencesViewMolder
import com.pmarchenko.itdroid.pocketkotlin.compose.ListSection
import com.pmarchenko.itdroid.pocketkotlin.compose.PocketKotlinTheme
import com.pmarchenko.itdroid.pocketkotlin.compose.TwoLineListItem
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput
import com.pmarchenko.itdroid.pocketkotlin.utils.dLog

/**
 * @author Pavel Marchenko
 */
@Composable
fun About() {
    val nav = navigation()
    val openLicensesAction = { nav.navigateTo(LicensesDestination) }

    val prefs = viewModel<AppPreferencesViewMolder>()
    val easterEggDialogShown by prefs.easterEggDialogShownObservable.observeAsState(true)
    val easterEggClickAction = if (easterEggDialogShown) {
        {}
    } else {
        val dialog = dialogs()
        var clickCount by remember { mutableStateOf(0) }
        ({
            clickCount += 1
            dLog { "Clicked: $clickCount" }
            if (clickCount >= 7) {
                dialog.show(EasterEggDialog)
                clickCount = 0
                prefs.easterEggDialogShown = true
            }
        })
    }

    AboutInput(
        openLicensesAction = openLicensesAction,
        easterEggClickAction = easterEggClickAction
    )
}

@Composable
private fun AboutInput(
    openLicensesAction: () -> Unit,
    easterEggClickAction: () -> Unit,
) {
    Column {
        ListSection(
            text = TextInput(R.string.screen__settings__section__about),
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        TwoLineListItem(
            title = TextInput(R.string.screen__settings__value__open_source_licenses),
            subtitle = TextInput(R.string.screen__settings__desc__open_source_licenses),
            onClick = openLicensesAction
        )

        TwoLineListItem(
            modifier = Modifier.clickable(indication = null, onClick = easterEggClickAction),
            title = TextInput(R.string.screen__settings__value__app_version),
            subtitle = TextInput(
                text = stringResource(
                    R.string.screen__settings__desc__app_version,
                    "${BuildConfig.VERSION_NAME}-${BuildConfig.BUILD_TYPE}",
                    BuildConfig.VERSION_CODE
                )
            )
        )
    }
}

@Preview("Settings:About preview [Light Theme]")
@Composable
private fun SettingsScreenPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        AboutInput(
            openLicensesAction = {},
            easterEggClickAction = {},
        )
    }
}

@Preview("Settings:About preview [Dark Theme]")
@Composable
private fun SettingsScreenPreview() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        AboutInput(
            openLicensesAction = {},
            easterEggClickAction = {},
        )
    }
}
