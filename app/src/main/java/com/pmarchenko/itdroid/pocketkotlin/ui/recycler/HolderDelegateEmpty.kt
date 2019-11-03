package com.pmarchenko.itdroid.pocketkotlin.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Pavel Marchenko
 */
@Suppress("unused")
class HolderDelegateEmpty private constructor(
    private val view: View?,
    @LayoutRes private val layoutId: Int
) :
    HolderDelegate<RecyclerView.ViewHolder, EmptyContentData>() {

    constructor(view: View) : this(view, NO_LAYOUT_RES)

    constructor(@LayoutRes layoutId: Int) : this(null, layoutId)

    override fun create(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(
            when {
                view != null -> view
                layoutId != NO_LAYOUT_RES -> inflater.inflate(layoutId, parent, false)
                else -> error("No empty view provided")
            }
        ) {}
    }

    override fun bind(
        holder: RecyclerView.ViewHolder,
        position: Int,
        contentData: EmptyContentData
    ) {
        //no-op
    }

    companion object {
        const val NO_LAYOUT_RES = -1
    }
}