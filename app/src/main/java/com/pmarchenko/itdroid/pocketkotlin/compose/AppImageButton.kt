package com.pmarchenko.itdroid.pocketkotlin.compose

import androidx.compose.foundation.AmbientContentColor
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pmarchenko.itdroid.pocketkotlin.utils.ImageInput
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput
import com.pmarchenko.itdroid.pocketkotlin.utils.tint

/**
 * @author Pavel Marchenko
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