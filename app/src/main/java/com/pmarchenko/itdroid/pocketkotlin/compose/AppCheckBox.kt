package com.pmarchenko.itdroid.pocketkotlin.compose

import androidx.compose.foundation.AmbientContentColor
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput

/**
 * @author Pavel Marchenko
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
            checkedColor = AmbientContentColor.current,
            onCheckedChange = onCheckedChange
        )

        AppText(
            text = text,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview("AppCheckBox: Preview")
@Composable
fun AppCheckBoxPreview() {
    AppCheckBox(
        text = TextInput(text = "Check box"),
        onCheckedChange = {}
    )
}