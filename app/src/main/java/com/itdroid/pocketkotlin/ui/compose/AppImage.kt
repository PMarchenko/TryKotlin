package com.itdroid.pocketkotlin.ui.compose

import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.utils.ImageInput

/**
 * @author itdroid
 */

@Composable
fun AppImage(
    image: ImageInput,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
) {
    when {
        image.hasImage() -> {
            Image(
                asset = image.image(),
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = image.colorFilter
            )
        }
        image.hasVector() -> {
            Image(
                asset = image.vector(),
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = image.colorFilter
            )
        }
        else -> error("No image asset provided")
    }
}

@Preview("AppImage preview [Light Theme]")
@Composable
private fun AppImagePreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        AppImage(ImageInput(Icons.Default.Add))
    }
}

@Preview("AppImage preview [Dark Theme]")
@Composable
private fun AppImagePreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        AppImage(ImageInput(Icons.Default.Add))
    }
}
