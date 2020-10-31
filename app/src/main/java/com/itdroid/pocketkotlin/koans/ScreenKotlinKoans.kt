package com.itdroid.pocketkotlin.koans

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
fun ScreenKotlinKoans() {
    Box(
        modifier = Modifier
            .background(Color.Magenta)
            .fillMaxSize()
    )
    //TODO implement me
}

@Preview("ScreenKotlinKoans preview [Light Theme]")
@Composable
private fun KotlinKoansScreenPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ScreenKotlinKoans()
    }
}

@Preview("ScreenKotlinKoans preview [Dark Theme]")
@Composable
private fun KotlinKoansScreenPreview() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ScreenKotlinKoans()
    }
}