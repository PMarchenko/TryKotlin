package com.pmarchenko.itdroid.pocketkotlin.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Pavel Marchenko
 */
open class HolderDelegateManager {

    private val delegates: MutableMap<Int, HolderDelegate<RecyclerView.ViewHolder, ContentData>> = mutableMapOf()

    protected fun <VH : RecyclerView.ViewHolder, C : ContentData> register(delegate: HolderDelegate<VH, C>) {
        @Suppress("UNCHECKED_CAST")
        delegates[delegate.viewType] = delegate as HolderDelegate<RecyclerView.ViewHolder, ContentData>
    }

    fun create(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegate(viewType).create(LayoutInflater.from(parent.context), parent)
    }

    fun bind(holder: RecyclerView.ViewHolder, position: Int, content: ContentData) {
        delegate(holder.itemViewType).bind(holder, position, content)
    }

    private fun delegate(viewType: Int) = delegates[viewType]
        ?: error("Unsupported view type: $viewType, please register a delegate object using #register(HolderDelegate) method")

}