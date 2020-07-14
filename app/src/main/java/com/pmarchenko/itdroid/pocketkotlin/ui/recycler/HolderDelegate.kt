package com.pmarchenko.itdroid.pocketkotlin.ui.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Pavel Marchenko
 */
abstract class HolderDelegate<VH : RecyclerView.ViewHolder, CD : ContentData> {

    abstract fun create(inflater: LayoutInflater, parent: ViewGroup): VH

    abstract fun bind(holder: VH, position: Int, data: CD)
}