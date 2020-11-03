package com.itdroid.pocketkotlin.syntax

/**
 * @author itdroid
 */
internal data class ColorConfig(
    val docCommentColor: Int,
    val commentColor: Int,
    val keywordColor: Int,
    val propNameColor: Int,
    val funNameColor: Int,
    val strCharLiteralColor: Int,
    val numberLiteralColor: Int,
)

internal val DarkThemeColorConfig = ColorConfig(
    docCommentColor = 0xFF499C54L.toInt(),
    commentColor = 0xFF808080L.toInt(),
    keywordColor = 0xFFCC7832L.toInt(),
    propNameColor = 0xFF9876AAL.toInt(),
    funNameColor = 0xFFFFC66DL.toInt(),
    strCharLiteralColor = 0xFF499C54L.toInt(),
    numberLiteralColor = 0xFF4D8ABAL.toInt(),
)

internal val LightThemeColorConfig = ColorConfig(
    docCommentColor = 0xFF499C54L.toInt(),
    commentColor = 0xFF808080L.toInt(),
    keywordColor = 0xFFCC7832L.toInt(),
    propNameColor = 0xFF9876AAL.toInt(),
    funNameColor = 0xFFFFC66DL.toInt(),
    strCharLiteralColor = 0xFF499C54L.toInt(),
    numberLiteralColor = 0xFF4D8ABAL.toInt(),
)