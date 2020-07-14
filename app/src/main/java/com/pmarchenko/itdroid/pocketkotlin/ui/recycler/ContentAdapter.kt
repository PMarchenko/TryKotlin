package com.pmarchenko.itdroid.pocketkotlin.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Pavel Marchenko
 */
abstract class ContentAdapter : DiffAdapter() {

    protected abstract val delegates: Map<Int, HolderDelegate<out RecyclerView.ViewHolder, out ContentData>>

    override fun getItemId(position: Int): Long = getItem(position).itemId

    override fun getItemViewType(position: Int) = getItem(position).viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        delegateFor(viewType).create(LayoutInflater.from(parent.context), parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateFor(holder.itemViewType).bind(holder, position, getItem(position))
    }

    @Suppress("UNCHECKED_CAST")
    private fun delegateFor(viewType: Int): HolderDelegate<RecyclerView.ViewHolder, ContentData> =
        delegates[viewType] as? HolderDelegate<RecyclerView.ViewHolder, ContentData>
            ?: error("Unsupported view type: $viewType")

}