package com.itdroid.pocketkotlin.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.preferredWidth
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.preferences.AppThemePreference

/**
 * @author itdroid
 */

@Composable
fun dividerColor() = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)

@Composable
fun AppDivider(
    modifier: Modifier = Modifier,
    color: Color = dividerColor(),
    thickness: Dp = 1.dp,
    startIndent: Dp = 0.dp,
) {
    Divider(
        modifier = modifier,
        color = color,
        thickness = thickness,
        startIndent = startIndent
    )
}

/**
 * Copied and reworked from [androidx.compose.material.Divider]
 * */
@Composable
fun AppDividerVertical(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
    thickness: Dp = 1.dp,
    startIndent: Dp = 0.dp,
) {
    val indentMod = if (startIndent.value != 0f) {
        Modifier.padding(top = startIndent)
    } else {
        Modifier
    }
    Box(
        modifier.then(indentMod)
            .fillMaxHeight()
            .preferredWidth(thickness)
            .background(color = color)
    )
}

@Preview("AppDivider preview [Light Theme]")
@Composable
private fun AppDividerPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        AppDivider()
    }
}

@Preview("AppCheckBox preview [Dark Theme]")
@Composable
private fun AppDividerPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        AppDivider()
    }
}

@Preview("AppDividerVertical preview [Light Theme]")
@Composable
private fun AppDividerVerticalPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        AppDividerVertical()
    }
}

@Preview("AppDividerVertical preview [Dark Theme]")
@Composable
private fun AppDividerVerticalPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        AppDividerVertical()
    }
}
