package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */
data class Position(
    val line: Int,
    val ch: Int
)

internal fun ApiPosition.fromApiPosition(): Position =
    Position(
        line = line,
        ch = ch
    )