package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.adapter

import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentData

/**
 * @author Pavel Marchenko
 */
class TextContentData(override val viewType: Int, private val text: String) : ContentData {

    override val itemId: Long = 0

    override fun isItemTheSame(data: ContentData): Boolean {
        return viewType == data.viewType &&
                if (data is TextContentData) {
                    text == data.text
                } else false
    }

    override fun isContentTheSame(data: ContentData): Boolean {
        return viewType == data.viewType &&
                if (data is TextContentData) {
                    text == data.text
                } else false
    }
}