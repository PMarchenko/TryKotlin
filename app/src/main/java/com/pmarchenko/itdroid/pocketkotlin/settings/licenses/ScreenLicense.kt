package com.pmarchenko.itdroid.pocketkotlin.settings.licenses

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.onActive
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.pmarchenko.itdroid.pocketkotlin.preferences.AppThemePreference
import com.pmarchenko.itdroid.pocketkotlin.compose.AppScreen
import com.pmarchenko.itdroid.pocketkotlin.compose.PocketKotlinTheme
import com.pmarchenko.itdroid.pocketkotlin.compose.state.UiState
import com.pmarchenko.itdroid.pocketkotlin.compose.state.defaultUiState
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput


/**
 * @author Pavel Marchenko
 */
@Composable
fun ScreenLicense(license: LicenseInfo) {
    val viewModel = viewModel<LicencesViewModel>()

    onActive {
        viewModel.loadLicense(license)
    }

    AppScreen(TextInput(text = license.name)) {
        val uiState by viewModel.licenseState.observeAsState(defaultUiState())
        ScreenLicenseInput(uiState)
    }
}

@Composable
private fun ScreenLicenseInput(
    uiState: UiState<String>,
) {
    uiState.consume { text ->
        ScrollableColumn(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.padding(16.dp),
                text = text,
                fontFamily = FontFamily.Monospace
            )
        }
    }
}

@Preview("ScreenLicense preview [Light Theme]")
@Composable
private fun ScreenLicensePreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ScreenLicenseInput(
            UiState("ScreenLicense preview [Light Theme]")
        )
    }
}

@Preview("ScreenLicense preview [Dark Theme]")
@Composable
private fun ScreenLicensePreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ScreenLicenseInput(
            UiState("ScreenLicense preview [Light Theme]")
        )
    }
}