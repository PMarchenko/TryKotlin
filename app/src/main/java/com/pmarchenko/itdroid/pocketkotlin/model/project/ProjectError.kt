package com.pmarchenko.itdroid.pocketkotlin.model.project

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

/**
 * @author Pavel Marchenko
 */
data class ProjectError(
        val message: String,
        val severity: ErrorSeverity,
        @SerializedName("className") val type: String,
        val interval: Interval
) : Parcelable {
    private constructor(parcel: Parcel) : this(
            message = parcel.readString()!!,
            severity = ErrorSeverity.valueOf(parcel.readString()!!),
            type = parcel.readString()!!,
            interval = parcel.readParcelable<Interval>(Interval::class.java.classLoader)!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(message)
        parcel.writeString(severity.name)
        parcel.writeString(type)
        parcel.writeParcelable(interval, interval.describeContents())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProjectError> {
        override fun createFromParcel(parcel: Parcel): ProjectError {
            return ProjectError(parcel)
        }

        override fun newArray(size: Int): Array<ProjectError?> {
            return arrayOfNulls(size)
        }
    }
}

data class Interval(val start: Position, val end: Position) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Position::class.java.classLoader)!!,
            parcel.readParcelable(Position::class.java.classLoader)!!)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(start, flags)
        parcel.writeParcelable(end, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Interval> {
        override fun createFromParcel(parcel: Parcel): Interval {
            return Interval(parcel)
        }

        override fun newArray(size: Int): Array<Interval?> {
            return arrayOfNulls(size)
        }
    }
}

data class Position(val line: Int, val ch: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readInt())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(line)
        parcel.writeInt(ch)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Position> {
        override fun createFromParcel(parcel: Parcel): Position {
            return Position(parcel)
        }

        override fun newArray(size: Int): Array<Position?> {
            return arrayOfNulls(size)
        }
    }
}

@Suppress("unused")
/**
 * Used for GSON serialization/deserialization
 * */
enum class ErrorSeverity {
    ERROR,
    WARNING,
}