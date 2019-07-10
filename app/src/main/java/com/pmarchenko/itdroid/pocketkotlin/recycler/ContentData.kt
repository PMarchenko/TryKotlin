package com.pmarchenko.itdroid.pocketkotlin.recycler

/**
 * @author Pavel Marchenko
 */
interface ContentData {

    val viewType: Int

    fun isItemTheSame(data: ContentData): Boolean

    fun isContentTheSame(data: ContentData): Boolean
}