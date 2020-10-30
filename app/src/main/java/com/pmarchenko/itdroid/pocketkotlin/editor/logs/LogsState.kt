package com.pmarchenko.itdroid.pocketkotlin.editor.logs

import android.os.Parcelable
import androidx.compose.foundation.lazy.LazyListState
import kotlinx.android.parcel.Parcelize

/**
 * @author Pavel Marchenko
 */
@Parcelize
data class LogsState(
    var autoScroll: Boolean = true,
    var firstVisibleItemIndex: Int = 0,
    var firstVisibleItemScrollOffset: Int = 0,
) : Parcelable {

    fun copyWith(
        autoScroll: Boolean = this.autoScroll,
        listState: LazyListState,
    ) =
        copy(
            autoScroll = autoScroll,
            firstVisibleItemIndex = listState.firstVisibleItemIndex,
            firstVisibleItemScrollOffset = listState.firstVisibleItemScrollOffset,
        )
}
