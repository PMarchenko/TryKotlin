package com.itdroid.pocketkotlin.ui.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.utils.TextInput

/**
 * @author itdroid
 */
@Composable
fun ErrorText(text: TextInput) {
    Box(modifier = Modifier.fillMaxSize()
        .padding(16.dp)) {
        AppText(
            text = text,
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colors.error,
        )
    }
}

@Preview("ErrorText preview [Light Theme]")
@Composable
private fun ErrorTextLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ErrorText(TextInput(text = "What a terrible failure!"))
    }
}

@Preview("ErrorText preview [Dark Theme]")
@Composable
private fun ErrorTextPreview() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ErrorText(TextInput(text = "What a terrible failure!"))
    }
}