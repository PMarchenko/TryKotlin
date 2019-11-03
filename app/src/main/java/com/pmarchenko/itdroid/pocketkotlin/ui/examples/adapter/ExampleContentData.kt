package com.pmarchenko.itdroid.pocketkotlin.ui.examples.adapter

import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.Example
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.ContentData

/**
 * @author Pavel Marchenko
 */
class ExampleContentData(
    override val viewType: Int,
    val example: Example
) : ContentData {

    override val itemId: Long = example.id

    override fun isItemTheSame(data: ContentData): Boolean {
        return if (data is ExampleContentData) {
            example.id == data.example.id
        } else {
            false
        }
    }

    override fun isContentTheSame(data: ContentData) = equals(data)

}