package com.pmarchenko.itdroid.pocketkotlin.ui.recycler

/**
 * @author Pavel Marchenko
 */
class EmptyContentData(override val viewType: Int) : ContentData {

    override val itemId: Long = 0

    override fun isItemTheSame(data: ContentData): Boolean {
        return viewType == data.viewType
    }

    override fun isContentTheSame(data: ContentData): Boolean {
        return equals(data)
    }
}