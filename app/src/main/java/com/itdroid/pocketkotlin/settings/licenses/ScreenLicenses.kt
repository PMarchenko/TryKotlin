package com.itdroid.pocketkotlin.settings.licenses

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
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.navigation.LicenseDestination
import com.itdroid.pocketkotlin.navigation.navigation
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.ui.compose.AppDivider
import com.itdroid.pocketkotlin.ui.compose.AppScreen
import com.itdroid.pocketkotlin.ui.compose.AppText
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.ui.compose.state.UiState
import com.itdroid.pocketkotlin.ui.compose.state.defaultUiState
import com.itdroid.pocketkotlin.utils.TextInput


/**
 * @author itdroid
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
