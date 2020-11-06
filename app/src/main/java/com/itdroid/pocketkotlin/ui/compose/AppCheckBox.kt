package com.itdroid.pocketkotlin.ui.compose

import androidx.compose.foundation.AmbientContentColor
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxConstants
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
fun AppCheckBox(
    modifier: Modifier = Modifier,
    checked: Boolean = true,
    text: TextInput,
    onCheckedChange: (Boolean) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable(onClick = { onCheckedChange(!checked) }).then(modifier)
    ) {
        Checkbox(
            checked = checked,
            colors = CheckboxConstants
                .defaultColors(checkedColor = AmbientContentColor.current),
            onCheckedChange = onCheckedChange
        )

        AppText(
            text = text,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview("AppCheckBox preview [Light Theme]")
@Composable
private fun AppCheckBoxPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        AppCheckBox(
            text = TextInput(text = "Check box")
        ) {}
    }
}

@Preview("AppCheckBox preview [Dark Theme]")
@Composable
private fun AppCheckBoxPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        AppCheckBox(
            text = TextInput(text = "Check box")
        ) {}
    }
}
