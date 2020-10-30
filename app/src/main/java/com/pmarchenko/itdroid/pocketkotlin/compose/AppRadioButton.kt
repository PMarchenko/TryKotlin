package com.pmarchenko.itdroid.pocketkotlin.compose

import androidx.compose.foundation.AmbientContentColor
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput

/**
 * @author Pavel Marchenko
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
            color = AmbientContentColor.current,
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