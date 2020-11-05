package com.itdroid.pocketkotlin.ui.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.preferences.AppThemePreference

/**
 * @author itdroid
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
