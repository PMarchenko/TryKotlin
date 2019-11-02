package com.pmarchenko.itdroid.pocketkotlin.ui.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Pavel Marchenko
 */
abstract class ContentAdapter : DiffAdapter<RecyclerView.ViewHolder>() {

    private val holderManager by lazy {
        val instance = HolderDelegateManager()
        for (delegate in delegates()) {
            instance.register(delegate.key, delegate.value)
        }
        instance
    }

    private var content: List<ContentData> = emptyList()

    override fun getItemCount() = content.size

    override fun getItemId(position: Int) = content[position].itemId

    override fun getItemViewType(position: Int) = content[position].viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = holderManager.create(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holderManager.bind(holder, position, content[position])
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)
        holderManager.attach(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holderManager.detach(holder)
    }

    protected fun setContent(newContent: List<ContentData>) {
        val oldContent = content
        content = newContent.toList()
        dispatchUpdates(oldContent, content)
    }

    protected fun <T : ContentData> getItem(position: Int): T {
        @Suppress("UNCHECKED_CAST")
        return content[position] as T
    }

    protected abstract fun delegates(): Map<Int, HolderDelegate<*, *>>
}