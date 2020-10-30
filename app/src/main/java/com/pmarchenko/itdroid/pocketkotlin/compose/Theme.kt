package com.pmarchenko.itdroid.pocketkotlin.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.editor.logs.LogsColorConfig
import com.pmarchenko.itdroid.pocketkotlin.preferences.AppThemePreference

/**
 * @author Pavel Marchenko
 */
@Composable
fun PocketKotlinTheme(
    appTheme: AppThemePreference = AppThemePreference.Auto,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colors = colors(appTheme),
        content = content
    )
}

@Composable
private fun colors(appTheme: AppThemePreference): Colors {
    val isLightTheme = when (appTheme) {
        AppThemePreference.Light -> true
        AppThemePreference.Dark -> false
        AppThemePreference.Auto -> isSystemInDarkTheme().not()
    }

    return if (isLightTheme) {
        lightColors(
            primary = colorResource(R.color.lightTheme_primary),
            primaryVariant = colorResource(R.color.lightTheme_primaryVariant),
            secondary = colorResource(R.color.lightTheme_secondary),
            background = colorResource(R.color.lightTheme_background),
            surface = colorResource(R.color.lightTheme_surface),
            onPrimary = colorResource(R.color.lightTheme_onPrimary),
            onSecondary = colorResource(R.color.lightTheme_onSecondary),
            onBackground = colorResource(R.color.lightTheme_onBackground),
            onSurface = colorResource(R.color.lightTheme_onSurface),
            error = colorResource(R.color.lightTheme_error),
            onError = colorResource(R.color.lightTheme_onError),
        )
    } else {
        darkColors(
            primary = colorResource(R.color.darkTheme_primary),
            primaryVariant = colorResource(R.color.darkTheme_primaryVariant),
            secondary = colorResource(R.color.darkTheme_secondary),
            background = colorResource(R.color.darkTheme_background),
            surface = colorResource(R.color.darkTheme_surface),
            onPrimary = colorResource(R.color.darkTheme_onPrimary),
            onSecondary = colorResource(R.color.darkTheme_onSecondary),
            onBackground = colorResource(R.color.darkTheme_onBackground),
            onSurface = colorResource(R.color.darkTheme_onSurface),
            error = colorResource(R.color.darkTheme_error),
            onError = colorResource(R.color.darkTheme_onError),
        )
    }
}

@Composable
val Colors.warning: Color
    get() = if (isLight) {
        colorResource(R.color.lightTheme_warning)
    } else {
        colorResource(R.color.darkTheme_warning)
    }

@Composable
val Colors.success: Color
    get() = if (isLight) {
        colorResource(R.color.lightTheme_success)
    } else {
        colorResource(R.color.darkTheme_success)
    }

@Composable
val Colors.logsConfig
    get() = LogsColorConfig(
        error = error,
        warning = warning,
        success = success,
    )