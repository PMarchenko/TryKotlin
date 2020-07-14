package com.pmarchenko.itdroid.pocketkotlin.ui.recycler

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Pavel Marchenko
 */
abstract class DiffAdapter : ListAdapter<ContentData, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    protected fun setContent(newItems: List<ContentData>) {
        submitList(newItems)
    }

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ContentData>() {
            override fun areItemsTheSame(oldItem: ContentData, newItem: ContentData): Boolean {
                return if (oldItem::class == newItem::class) {
                    oldItem.isItemTheSame(newItem)
                } else {
                    false
                }
            }

            override fun areContentsTheSame(oldItem: ContentData, newItem: ContentData): Boolean {
                return if (oldItem::class == newItem::class) {
                    oldItem.isContentTheSame(newItem)
                } else {
                    false
                }
            }
        }
    }
}