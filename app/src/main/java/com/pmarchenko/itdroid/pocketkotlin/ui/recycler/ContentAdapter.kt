package com.pmarchenko.itdroid.pocketkotlin.ui.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Pavel Marchenko
 */
abstract class ContentAdapter : DiffAdapter() {

    private val holderManager by lazy {
        val instance = HolderDelegateManager()
        for (delegate in delegates()) {
            instance.register(delegate.key, delegate.value)
        }
        instance
    }

    override fun getItemId(position: Int): Long = getItem(position).itemId

    override fun getItemViewType(position: Int): Int = getItem(position).viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        holderManager.create(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holderManager.bind(holder, position, getItem(position))
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holderManager.attach(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holderManager.detach(holder)
    }

    protected fun setContent(content: List<ContentData>) {
        dispatchUpdates(content)
    }

    protected abstract fun delegates(): Map<Int, HolderDelegate<*, *>>
}