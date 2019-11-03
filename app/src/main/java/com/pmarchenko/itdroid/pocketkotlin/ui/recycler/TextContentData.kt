package com.pmarchenko.itdroid.pocketkotlin.ui.recycler

/**
 * @author Pavel Marchenko
 */
class TextContentData(override val viewType: Int, val text: CharSequence) : ContentData {

    override val itemId: Long = 0

    override fun isItemTheSame(data: ContentData): Boolean {
        return viewType == data.viewType &&
                if (data is TextContentData) {
                    text == data.text
                } else {
                    false
                }
    }

    override fun isContentTheSame(data: ContentData): Boolean = equals(data)

}