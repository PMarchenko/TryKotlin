package com.itdroid.pocketkotlin.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.dialog.NoneAppDialog
import com.itdroid.pocketkotlin.dialog.dialogs
import com.itdroid.pocketkotlin.editor.ScreenEditor
import com.itdroid.pocketkotlin.editor.configuration.ScreenProjectConfiguration
import com.itdroid.pocketkotlin.navigation.*
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.settings.licenses.ScreenLicense
import com.itdroid.pocketkotlin.settings.licenses.ScreenLicenses
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.utils.checkAllMatched

/**
 * @author itdroid
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