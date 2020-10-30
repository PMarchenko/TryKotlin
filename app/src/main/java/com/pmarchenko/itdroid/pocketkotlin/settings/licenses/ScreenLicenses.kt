package com.pmarchenko.itdroid.pocketkotlin.settings.licenses

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.material.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.onActive
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.navigation.LicenseDestination
import com.pmarchenko.itdroid.pocketkotlin.navigation.navigation
import com.pmarchenko.itdroid.pocketkotlin.preferences.AppThemePreference
import com.pmarchenko.itdroid.pocketkotlin.compose.AppDivider
import com.pmarchenko.itdroid.pocketkotlin.compose.AppScreen
import com.pmarchenko.itdroid.pocketkotlin.compose.AppText
import com.pmarchenko.itdroid.pocketkotlin.compose.PocketKotlinTheme
import com.pmarchenko.itdroid.pocketkotlin.compose.state.UiState
import com.pmarchenko.itdroid.pocketkotlin.compose.state.defaultUiState
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput


/**
 * @author Pavel Marchenko
 */
@Composable
fun ScreenLicenses() {
    val vm = viewModel<LicencesViewModel>()

    onActive {
        vm.loadLicenses()
    }

    AppScreen(TextInput(R.string.screen__open_source_licenses)) {
        val uiState by vm.licensesState.observeAsState(defaultUiState())
        val nav = navigation()
        val openLicenseAction: (LicenseInfo) -> Unit = { nav.navigateTo(LicenseDestination(it)) }
        ScreenLicensesInput(
            uiState = uiState,
            openLicenseAction = openLicenseAction,
        )
    }
}

@Composable
private fun ScreenLicensesInput(
    uiState: UiState<List<LicenseInfo>>,
    openLicenseAction: (LicenseInfo) -> Unit,
) {
    uiState.consume { LicensesList(it, openLicenseAction) }
}

@Composable
private fun LicensesList(
    licenses: List<LicenseInfo>,
    openLicenseAction: (LicenseInfo) -> Unit,
) {
    LazyColumnFor(licenses) { license ->
        ListItem(
            modifier = Modifier.clickable(onClick = { openLicenseAction(license) }),
            text = { AppText(TextInput(text = license.name)) },
        )

        AppDivider()
    }
}

@Preview("OpenSourceLicensesScreen preview [Light Theme]")
@Composable
private fun OpenSourceLicensesScreenPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ScreenLicensesInput(
            uiState = UiState(
                listOf(
                    LicenseInfo("First", 0, 0),
                    LicenseInfo("Second", 0, 0),
                    LicenseInfo("Third", 0, 0),
                )
            ),
            openLicenseAction = {}
        )
    }
}

@Preview("OpenSourceLicensesScreen preview [Dark Theme]")
@Composable
private fun OpenSourceLicensesScreenPreview() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ScreenLicensesInput(
            uiState = UiState(
                listOf(
                    LicenseInfo("First", 0, 0),
                    LicenseInfo("Second", 0, 0),
                    LicenseInfo("Third", 0, 0),
                )
            ),
            openLicenseAction = {}
        )
    }
}
