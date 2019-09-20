package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.adapter

import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentData

/**
 * @author Pavel Marchenko
 */
class EmptyContentData(override val viewType: Int) : ContentData {

    override val itemId: Long = 0

    override fun isItemTheSame(data: ContentData): Boolean {
        return viewType == data.viewType
    }

    override fun isContentTheSame(data: ContentData): Boolean {
        return viewType == data.viewType
    }
}