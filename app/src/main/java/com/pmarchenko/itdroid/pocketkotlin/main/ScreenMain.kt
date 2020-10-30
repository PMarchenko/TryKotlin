package com.pmarchenko.itdroid.pocketkotlin.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.ui.tooling.preview.Preview
import com.pmarchenko.itdroid.pocketkotlin.dialog.NoneAppDialog
import com.pmarchenko.itdroid.pocketkotlin.dialog.dialogs
import com.pmarchenko.itdroid.pocketkotlin.editor.ScreenEditor
import com.pmarchenko.itdroid.pocketkotlin.editor.configuration.ScreenProjectConfiguration
import com.pmarchenko.itdroid.pocketkotlin.navigation.*
import com.pmarchenko.itdroid.pocketkotlin.preferences.AppThemePreference
import com.pmarchenko.itdroid.pocketkotlin.settings.licenses.ScreenLicense
import com.pmarchenko.itdroid.pocketkotlin.settings.licenses.ScreenLicenses
import com.pmarchenko.itdroid.pocketkotlin.compose.PocketKotlinTheme
import com.pmarchenko.itdroid.pocketkotlin.utils.checkAllMatched

/**
 * @author Pavel Marchenko
 */
@Composable
fun ScreenMain() {
    val destinationState = navigation().destination.observeAsState()
    ScreenMainInput(destinationState.value)

    val dialog = dialogs()
    val dialogState by dialog.currentDialog.observeAsState(NoneAppDialog)
    dialogState.show { dialog.dismiss() }
}

@Composable
private fun ScreenMainInput(destination: Destination?) {
    when (destination) {
        is DrawerDestination -> AppDrawerScreen(destination)
        LicensesDestination -> ScreenLicenses()
        is LicenseDestination -> ScreenLicense(destination.licenseInfo)
        is ProjectEditorDestination -> ScreenEditor(destination.projectId, destination.fileId)
        is ProjectConfigurationDestination -> ScreenProjectConfiguration(destination.projectId)
        null -> error("Unsupported destination - $destination")
    }.checkAllMatched
}

@Preview("ScreenMain preview [Light Theme]")
@Composable
private fun ScreenMainPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ScreenMainInput(MyProjectsDestination)
    }
}

@Preview("ScreenMain preview [Dark Theme]")
@Composable
private fun ScreenMainPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ScreenMainInput(MyProjectsDestination)
    }
}