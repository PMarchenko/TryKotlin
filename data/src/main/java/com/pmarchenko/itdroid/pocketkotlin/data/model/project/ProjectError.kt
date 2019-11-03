package com.pmarchenko.itdroid.pocketkotlin.data.model.project

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * @author Pavel Marchenko
 */
@Parcelize
data class ProjectError(
    val message: String,
    val severity: ErrorSeverity,
    @SerializedName("className") val type: String,
    val interval: Interval
) : Parcelable

@Parcelize
data class Interval(val start: Position, val end: Position) : Parcelable

@Parcelize
data class Position(val line: Int, val ch: Int) : Parcelable

@Suppress("unused")
/**
 * Used for GSON serialization/deserialization
 * */
enum class ErrorSeverity { ERROR, WARNING }