package com.pmarchenko.itdroid.pocketkotlin.recycler

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Pavel Marchenko
 */
abstract class DiffAdapter<T : RecyclerView.ViewHolder> : RecyclerView.Adapter<T>() {

    fun dispatchUpdates(oldItems: ArrayList<ContentData>, newItems: ArrayList<ContentData>) {
        val result = DiffUtil.calculateDiff(DiffCallback(oldItems, newItems))
        result.dispatchUpdatesTo(this)
    }

    private class DiffCallback(val oldItems: ArrayList<ContentData>, val newItems: List<ContentData>) : DiffUtil.Callback() {

        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].isItemTheSame(newItems[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].isContentTheSame(newItems[newItemPosition])
        }
    }
}