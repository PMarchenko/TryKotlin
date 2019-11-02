package com.pmarchenko.itdroid.pocketkotlin.model

import android.os.Parcel
import android.os.Parcelable
import com.pmarchenko.itdroid.pocketkotlin.model.project.ErrorSeverity

/**
 *
 * @author Pavel Marchenko
 */
data class EditorError(
    val message: String,
    val severity: ErrorSeverity,
    val startLine: Int,
    val endLine: Int,
    val start: Int,
    val end: Int
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        ErrorSeverity.valueOf(parcel.readString() ?: ErrorSeverity.ERROR.name),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(message)
        parcel.writeString(severity.name)
        parcel.writeInt(startLine)
        parcel.writeInt(endLine)
        parcel.writeInt(start)
        parcel.writeInt(end)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EditorError> {
        override fun createFromParcel(parcel: Parcel): EditorError {
            return EditorError(parcel)
        }

        override fun newArray(size: Int): Array<EditorError?> {
            return arrayOfNulls(size)
        }
    }
}