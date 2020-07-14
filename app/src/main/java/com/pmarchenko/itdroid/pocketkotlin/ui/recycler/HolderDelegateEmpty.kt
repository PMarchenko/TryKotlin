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
class HolderDelegateEmpty private constructor(private val viewProvider: EmptyViewProvider) :
    HolderDelegate<RecyclerView.ViewHolder, EmptyContentData>() {

    constructor(view: View) : this(ViewEmptyViewProvider(view))

    constructor(@LayoutRes layoutId: Int) : this(LayoutIdEmptyViewProvider(layoutId))

    override fun create(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(viewProvider.get(inflater, parent)) {}
    }

    override fun bind(holder: RecyclerView.ViewHolder, position: Int, data: EmptyContentData) {
    }
}

private sealed class EmptyViewProvider {
    abstract fun get(inflater: LayoutInflater, parent: ViewGroup): View
}

private class ViewEmptyViewProvider(val view: View) : EmptyViewProvider() {
    override fun get(inflater: LayoutInflater, parent: ViewGroup) = view
}

private class LayoutIdEmptyViewProvider(@LayoutRes val id: Int) : EmptyViewProvider() {
    override fun get(inflater: LayoutInflater, parent: ViewGroup): View =
        inflater.inflate(id, parent, false)
}