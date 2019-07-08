package com.pmarchenko.itdroid.pocketkotlin.model

import com.google.gson.annotations.SerializedName

/**
 * @author Pavel Marchenko
 */
data class ProjectError(
    val message: String,
    val severity: ErrorSeverity,
    @SerializedName("className") val type: String,
    val interval: Interval
)

data class Interval(val start: Position, val end: Position)

data class Position(val line: Int, val ch: Int)

@Suppress("unused")
/**
 * Used for GSON serialization/deserialization
 * */
enum class ErrorSeverity {
    ERROR,
    WARNING,
}