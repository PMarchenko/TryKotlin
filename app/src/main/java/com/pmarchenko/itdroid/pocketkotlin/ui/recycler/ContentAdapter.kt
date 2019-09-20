package com.pmarchenko.itdroid.pocketkotlin.ui.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Pavel Marchenko
 */
abstract class ContentAdapter(private val holderManager: HolderDelegateManager) : DiffAdapter<RecyclerView.ViewHolder>() {

    private var content: List<ContentData> = emptyList()

    override fun getItemCount() = content.size

    override fun getItemId(position: Int) = content[position].itemId

    override fun getItemViewType(position: Int) = content[position].viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = holderManager.create(parent, viewType)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holderManager.bind(holder, position, content[position])
    }

    protected fun setContent(newContent: List<ContentData>) {
        val oldContent = content
        content = newContent.toList()
        dispatchUpdates(oldContent, content)
    }

    protected fun <T : ContentData> getItem(position: Int): T {
        return content[position] as T
    }
}