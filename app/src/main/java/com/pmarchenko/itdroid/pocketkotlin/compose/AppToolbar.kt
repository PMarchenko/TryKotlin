package com.pmarchenko.itdroid.pocketkotlin.compose

import androidx.compose.foundation.AmbientContentColor
import androidx.compose.foundation.ProvideTextStyle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pmarchenko.itdroid.pocketkotlin.utils.ImageInput
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput
import com.pmarchenko.itdroid.pocketkotlin.utils.tint

/**
 * @author Pavel Marchenko
 */

@Composable
fun Toolbar(
    icon: ImageInput? = null,
    title: TextInput,
    backgroundColor: Color = MaterialTheme.colors.primarySurface,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: Dp = 4.dp,
    actions: @Composable RowScope.() -> Unit = {},
    iconOnClick: (() -> Unit)? = null,
) {
    TopAppBar(
        title = {
            AppText(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        elevation = elevation,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        actions = actions,
        navigationIcon = icon?.let {
            {
                if (iconOnClick == null) {
                    AppImage(it.tint(AmbientContentColor.current))
                } else {
                    IconButton(iconOnClick) {
                        AppImage(it.tint(AmbientContentColor.current))
                    }
                }
            }
        }
    )
}

@Composable
fun ToolbarAction(
    image: ImageInput? = null,
    text: TextInput? = null,
    onClick: () -> Unit,
) {
    Surface(
        shape = CircleShape,
        elevation = 0.dp,
        color = Color.Unspecified,
        contentColor = ButtonConstants.defaultButtonContentColor(
            true,
            contentColorFor(ButtonConstants.defaultButtonBackgroundColor(true))
        ),
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = onClick)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ProvideTextStyle(
                value = MaterialTheme.typography.button
            ) {
                image?.let {
                    AppImage(it.tint(AmbientContentColor.current))
                    if (text != null) {
                        Spacer(Modifier.size(4.dp))
                    }
                }

                text?.let { AppText(it) }
            }
        }
    }
}