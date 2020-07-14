package com.pmarchenko.itdroid.pocketkotlin.ui.editor.model

import android.os.Parcelable
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ErrorSeverity
import kotlinx.android.parcel.Parcelize

/**
 *
 * @author Pavel Marchenko
 */
@Parcelize
data class EditorError(
    val message: String,
    val severity: ErrorSeverity,
    val startLine: Int,
    val endLine: Int,
    val start: Int,
    val end: Int
) : Parcelable