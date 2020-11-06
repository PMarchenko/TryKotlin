package com.itdroid.pocketkotlin.main


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.dialog.NoneAppDialog
import com.itdroid.pocketkotlin.dialog.dialogs
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.settings.preferences
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme

@Composable
fun PocketKotlinApp() {
    val appTheme by preferences()
        .appThemeObservable
        .observeAsState(AppThemePreference.Auto)

    PocketKotlinAppInput(appTheme)
}

@Composable
private fun PocketKotlinAppInput(appTheme: AppThemePreference) {
    PocketKotlinTheme(appTheme) {
        Surface(modifier = Modifier.fillMaxSize()) {
            ScreenMain()
        }

        val dialog = dialogs()
        val dialogState by dialog.currentDialog.observeAsState(NoneAppDialog)
        dialogState.show { dialog.dismiss() }
    }
}

@Preview
@Composable
private fun PocketKotlinAppPreview() {
    PocketKotlinApp()
}

@Preview("PocketKotlinApp preview [Light Theme]")
@Composable
private fun PocketKotlinAppPreviewLightTheme() {
    PocketKotlinAppInput(AppThemePreference.Light)
}

@Preview("PocketKotlinApp preview [Dark Theme]")
@Composable
private fun PocketKotlinAppPreviewDarkTheme() {
    PocketKotlinAppInput(AppThemePreference.Dark)
}