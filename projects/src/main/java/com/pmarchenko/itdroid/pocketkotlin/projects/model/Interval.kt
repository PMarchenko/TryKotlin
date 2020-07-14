package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */
data class Interval(
    val start: Position,
    val end: Position
)

internal fun ApiInterval.fromApiInterval(): Interval =
    Interval(
        start = start.fromApiPosition(),
        end = start.fromApiPosition()
    )