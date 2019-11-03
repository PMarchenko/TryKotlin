package com.pmarchenko.itdroid.pocketkotlin.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Pavel Marchenko
 */
class HolderDelegateManager {

    private val delegates: MutableMap<Int, HolderDelegate<RecyclerView.ViewHolder, ContentData>> =
        mutableMapOf()

    fun <VH : RecyclerView.ViewHolder, C : ContentData> register(
        viewType: Int,
        delegate: HolderDelegate<VH, C>
    ) {
        @Suppress("UNCHECKED_CAST")
        delegates[viewType] = delegate as HolderDelegate<RecyclerView.ViewHolder, ContentData>
    }

    fun create(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegate(viewType).create(LayoutInflater.from(parent.context), parent)
    }

    fun bind(holder: RecyclerView.ViewHolder, position: Int, content: ContentData) {
        delegate(holder.itemViewType).bind(holder, position, content)
    }


    fun attach(holder: RecyclerView.ViewHolder) {
        delegate(holder.itemViewType).attach(holder)
    }

    fun detach(holder: RecyclerView.ViewHolder) {
        delegate(holder.itemViewType).detach(holder)
    }

    private fun delegate(viewType: Int) =
        delegates[viewType]
            ?: error("Unsupported view type: $viewType, please register a delegate object using #register(HolderDelegate) method")

}