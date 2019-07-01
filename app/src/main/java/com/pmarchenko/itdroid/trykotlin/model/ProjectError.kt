package com.pmarchenko.itdroid.trykotlin.model

import com.google.gson.annotations.SerializedName

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
data class ProjectError(
    val message: String,
    val severity: ErrorSeverity,
    @SerializedName("className") val type: String,
    val interval: Interval
)

data class Interval(val start: Position, val end: Position)

data class Position(val line: Int, val pos: Int)

enum class ErrorSeverity {
    ERROR,
}