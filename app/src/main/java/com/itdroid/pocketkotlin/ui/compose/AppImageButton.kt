package com.itdroid.pocketkotlin.ui.compose

import androidx.compose.foundation.AmbientContentColor
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.utils.ImageInput
import com.itdroid.pocketkotlin.utils.TextInput
import com.itdroid.pocketkotlin.utils.tint

/**
 * @author itdroid
 */
@Composable
fun AppImageButton(
    modifier: Modifier = Modifier,
    startImage: ImageInput? = null,
    text: TextInput? = null,
    endImage: ImageInput? = null,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled,
    ) {
        startImage?.let {
            AppImage(
                modifier = Modifier.size(16.dp),
                image = it.tint(AmbientContentColor.current)
            )
        }
        text?.let { AppText(it) }
        endImage?.let {
            AppImage(
                modifier = Modifier.size(16.dp),
                image = it.tint(AmbientContentColor.current)
            )
        }
    }
}

@Preview("AppImageButton preview [Light Theme]")
@Composable
private fun AppImageButtonPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        AppImageButton(
            startImage = ImageInput(Icons.Default.Add),
            text = TextInput(text = "Button"),
            endImage = ImageInput(Icons.Default.AddCircle),
        ) {}
    }
}

@Preview("AppImageButton preview [Dark Theme]")
@Composable
private fun AppImageButtonPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        AppImageButton(
            startImage = ImageInput(Icons.Default.Add),
            text = TextInput(text = "Button"),
            endImage = ImageInput(Icons.Default.AddCircle),
        ) {}
    }
}
