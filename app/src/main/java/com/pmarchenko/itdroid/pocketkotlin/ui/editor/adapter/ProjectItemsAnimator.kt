package com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class ProjectItemsAnimator : DefaultItemAnimator() {

    override fun canReuseUpdatedViewHolder(
        viewHolder: RecyclerView.ViewHolder, payloads: MutableList<Any>
    ) = true

    override fun canReuseUpdatedViewHolder(viewHolder: RecyclerView.ViewHolder) = true
}
