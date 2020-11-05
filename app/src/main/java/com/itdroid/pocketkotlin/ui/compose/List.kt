package com.itdroid.pocketkotlin.ui.compose

import androidx.compose.foundation.AmbientTextStyle
import androidx.compose.foundation.ProvideTextStyle
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ListItem
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.utils.TextInput

/**
 * @author itdroid
 */

@Composable
fun TwoLineListItem(
    modifier: Modifier = Modifier,
    title: TextInput,
    titleStyle: TextStyle? = null,
    subtitle: TextInput,
    subtitleStyle: TextStyle? = null,
    onClick: (() -> Unit)? = null,
) {
    ListItem(
        modifier = onClick?.let { modifier.clickable(onClick = onClick) } ?: modifier,
        text = {
            ProvideTextStyle(titleStyle ?: AmbientTextStyle.current) {
                Text(title.text())
            }
        },
        secondaryText = {
            ProvideTextStyle(subtitleStyle ?: AmbientTextStyle.current) {
                Text(subtitle.text())
            }
        }
    )
}

@Composable
fun ListSection(
    text: TextInput,
    modifier: Modifier = Modifier,
) {
    AppText(
        text = text,
        modifier = modifier.padding(top = 16.dp, bottom = 8.dp),
        style = MaterialTheme.typography.body2
    )
}

@Preview("TwoLineListItem preview [Light Theme]")
@Composable
private fun TwoLineListItemPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        TwoLineListItem(
            title = TextInput(text = "Title"),
            subtitle = TextInput(text = "Subtitle")
        )
    }
}

@Preview("TwoLineListItem preview [Dark Theme]")
@Composable
private fun TwoLineListItemPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        TwoLineListItem(
            title = TextInput(text = "Title"),
            subtitle = TextInput(text = "Subtitle")
        )
    }
}

@Preview("ListSection preview [Light Theme]")
@Composable
private fun ListSectionPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ListSection(TextInput(text = "Title"))
    }
}

@Preview("ListSection preview [Dark Theme]")
@Composable
private fun ListSectionPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ListSection(TextInput(text = "Title"))
    }
}
