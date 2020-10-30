package com.pmarchenko.itdroid.pocketkotlin.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.ui.tooling.preview.Preview
import com.pmarchenko.itdroid.pocketkotlin.preferences.AppThemePreference

/**
 * @author Pavel Marchenko
 */

@Preview
@Composable
fun ProgressIndicator(
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colors.secondary
        )
    }
}

@Preview("ProgressIndicator preview [Light Theme]")
@Composable
private fun ProgressIndicatorLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ProgressIndicator()
    }
}

@Preview("ProgressIndicator preview [Dark Theme]")
@Composable
private fun ProgressIndicatorPreview() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ProgressIndicator()
    }
}