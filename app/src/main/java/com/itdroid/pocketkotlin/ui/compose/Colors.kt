package com.itdroid.pocketkotlin.ui.compose

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.editor.logs.LogsColorConfig
import com.itdroid.pocketkotlin.syntax.SyntaxColorConfig

/**
 * @author itdroid
 */

@Composable
val Colors.editTextActiveColor: Color
    get() = secondary

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
val Colors.logsColorConfig: LogsColorConfig
    get() = LogsColorConfig(
        error = error,
        warning = warning,
        success = success,
    )

@Composable
val Colors.syntaxColorConfig: SyntaxColorConfig
    get() =
        if (isLight)
            SyntaxColorConfig(
                isLightColors = true,
                docCommentColor = colorResource(id = R.color.lightTheme_docComment).toArgb(),
                commentColor = colorResource(id = R.color.lightTheme_comment).toArgb(),
                keywordColor = colorResource(id = R.color.lightTheme_keyword).toArgb(),
                propNameColor = colorResource(id = R.color.lightTheme_propertyName).toArgb(),
                funNameColor = colorResource(id = R.color.lightTheme_functionName).toArgb(),
                strCharLiteralColor = colorResource(id = R.color.lightTheme_strCharLiteral).toArgb(),
                numberLiteralColor = colorResource(id = R.color.lightTheme_numberLiteral).toArgb(),
                annotationColor = colorResource(id = R.color.lightTheme_annotation).toArgb(),
                atSuffixColor =  colorResource(id = R.color.lightTheme_atSuffix).toArgb(),
            )
        else
            SyntaxColorConfig(
                isLightColors = false,
                docCommentColor = colorResource(id = R.color.darkTheme_docComment).toArgb(),
                commentColor = colorResource(id = R.color.darkTheme_comment).toArgb(),
                keywordColor = colorResource(id = R.color.darkTheme_keyword).toArgb(),
                propNameColor = colorResource(id = R.color.darkTheme_propertyName).toArgb(),
                funNameColor = colorResource(id = R.color.darkTheme_functionName).toArgb(),
                strCharLiteralColor = colorResource(id = R.color.darkTheme_strCharLiteral).toArgb(),
                numberLiteralColor = colorResource(id = R.color.darkTheme_numberLiteral).toArgb(),
                annotationColor = colorResource(id = R.color.darkTheme_annotation).toArgb(),
                atSuffixColor =  colorResource(id = R.color.lightTheme_atSuffix).toArgb(),
            )
