package com.pmarchenko.itdroid.pocketkotlin.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Pavel Marchenko
 */
abstract class HolderDelegate<VH : RecyclerView.ViewHolder, CD : ContentData> {

    abstract fun create(inflater: LayoutInflater, parent: ViewGroup): VH

    abstract fun bind(holder: VH, position: Int, contentData: CD)

    open fun attach(holder: VH) {
    }

    open fun detach(holder: VH) {
    }
}