package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */
data class Position internal constructor(
    val line: Int,
    val ch: Int,
)

internal fun ApiPosition.fromApiPosition(): Position =
    Position(
        line = line ?: 0,
        ch = ch ?: 0
    )