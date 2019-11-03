package com.pmarchenko.itdroid.pocketkotlin.ui.recycler

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Pavel Marchenko
 */
abstract class DiffAdapter<T : RecyclerView.ViewHolder> : RecyclerView.Adapter<T>() {

    protected fun dispatchUpdates(oldItems: List<ContentData>, newItems: List<ContentData>) {
        val result = DiffUtil.calculateDiff(DiffCallback(oldItems, newItems))
        result.dispatchUpdatesTo(this)
    }

    private class DiffCallback(
        val oldItems: List<ContentData>, val newItems: List<ContentData>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return if (oldItem::class == newItem::class) {
                oldItem.isItemTheSame(newItem)
            } else {
                false
            }
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldItems[oldItemPosition]
            val newItem = newItems[newItemPosition]

            return if (oldItem::class == newItem::class) {
                oldItem.isContentTheSame(newItem)
            } else {
                false
            }
        }
    }
}