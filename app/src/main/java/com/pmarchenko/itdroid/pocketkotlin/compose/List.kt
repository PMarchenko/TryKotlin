package com.pmarchenko.itdroid.pocketkotlin.compose

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
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput

/**
 * @author Pavel Marchenko
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
