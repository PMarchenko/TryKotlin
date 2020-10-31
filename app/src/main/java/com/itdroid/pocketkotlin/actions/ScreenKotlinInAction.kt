package com.itdroid.pocketkotlin.actions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme

/**
 * @author itdroid
 */
@Composable
fun ScreenKotlinInAction() {
    Box(
        modifier = Modifier
            .background(Color.Green)
            .fillMaxSize()
    )
    //TODO implement me
}

@Preview("KotlinInActionScreen preview [Light Theme]")
@Composable
private fun KotlinKoansScreenPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ScreenKotlinInAction()
    }
}

@Preview("KotlinInActionScreen preview [Dark Theme]")
@Composable
private fun KotlinKoansScreenPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ScreenKotlinInAction()
    }
}