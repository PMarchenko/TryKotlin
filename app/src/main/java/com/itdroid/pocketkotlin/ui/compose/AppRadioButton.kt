package com.itdroid.pocketkotlin.ui.compose

import androidx.compose.foundation.AmbientContentColor
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonConstants
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
fun <T> AppRadioButton(
    item: T,
    modifier: Modifier = Modifier,
    selected: Boolean = true,
    text: TextInput? = null,
    onSelected: (T) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = { onSelected(item) }).then(modifier)
    ) {
        RadioButton(
            selected = selected,
            colors = RadioButtonConstants
                .defaultColors(selectedColor = AmbientContentColor.current),
            onClick = { onSelected(item) }
        )

        if (text != null) {
            AppText(
                text = text,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview("AppRadioButton preview [Light Theme]")
@Composable
private fun AppRadioButtonPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        AppRadioButton(
            item = "",
            text = TextInput(text = "AppRadioButton"),
        ) {}
    }
}

@Preview("AppRadioButton preview [Dark Theme]")
@Composable
private fun AppRadioButtonPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        AppRadioButton(
            item = "",
            text = TextInput(text = "AppRadioButton"),
        ) {}
    }
}
