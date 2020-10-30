package com.pmarchenko.itdroid.pocketkotlin.compose

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.pmarchenko.itdroid.pocketkotlin.navigation.navigation
import com.pmarchenko.itdroid.pocketkotlin.preferences.AppThemePreference
import com.pmarchenko.itdroid.pocketkotlin.utils.ImageInput
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput

/**
 * @author Pavel Marchenko
 */

@Composable
fun AppScreen(
    title: TextInput? = null,
    toolbar: @Composable (BoxScope.() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val nav = navigation()

    AppScreenInput(
        title = title,

        upNavAction = { nav.popCurrentScreen() },

        toolbar = toolbar,
        content = content,
    )
}

@Composable
private fun AppScreenInput(
    title: TextInput? = null,
    upNavAction: (() -> Unit)? = null,
    toolbar: @Composable (BoxScope.() -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        when {
            title != null ->
                Toolbar(
                    title = title,
                    icon = ImageInput(vector = Icons.Filled.ArrowBack),
                    iconOnClick = upNavAction,
                )
            toolbar != null ->
                Surface(
                    elevation = 4.dp,
                    color = MaterialTheme.colors.primarySurface,
                    contentColor = contentColorFor(MaterialTheme.colors.primarySurface),
                ) {
                    Box(children = toolbar)
                }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            children = content
        )
    }
}

@Preview("AppScreen preview [Light Theme]")
@Composable
private fun AppScreenPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        AppScreenInput(TextInput(text = "AppScreen")) {
            Text(text = "Content")
        }
    }
}

@Preview("AppScreen preview [Dark Theme]")
@Composable
private fun AppScreenPreview() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        AppScreenInput(TextInput(text = "AppScreen")) {
            Text(text = "Content")
        }
    }
}