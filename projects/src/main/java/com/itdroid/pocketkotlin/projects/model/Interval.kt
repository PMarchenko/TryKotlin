package com.itdroid.pocketkotlin.projects.model

/**
 * @author itdroid
 */
data class Interval internal constructor(
    val start: Position,
    val end: Position
)

internal fun ApiInterval.fromApiInterval(): Interval =
    Interval(
        start = start?.fromApiPosition() ?: Position(0, 0),
        end = start?.fromApiPosition() ?: Position(0, 0)
    )