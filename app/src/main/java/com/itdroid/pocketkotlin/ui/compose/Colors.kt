package com.itdroid.pocketkotlin.ui.compose

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.editor.logs.LogsColorConfig

/**
 * @author itdroid
 */
@Composable
fun editTextActiveColor() = MaterialTheme.colors.secondary

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