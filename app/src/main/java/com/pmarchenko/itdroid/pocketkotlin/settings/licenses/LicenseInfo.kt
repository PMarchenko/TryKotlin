package com.pmarchenko.itdroid.pocketkotlin.settings.licenses

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Pavel Marchenko
 */
@Parcelize
data class LicenseInfo(
    val name: String,
    val offset: Long,
    val length: Int
) : Parcelable