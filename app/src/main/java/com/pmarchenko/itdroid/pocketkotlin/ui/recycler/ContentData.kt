package com.pmarchenko.itdroid.pocketkotlin.ui.recycler

/**
 * @author Pavel Marchenko
 */
interface ContentData {

    val viewType: Int

    val itemId: Long

    fun isItemTheSame(data: ContentData): Boolean

    fun isContentTheSame(data: ContentData): Boolean
}