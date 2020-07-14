package com.pmarchenko.itdroid.pocketkotlin.ui.recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Pavel Marchenko
 */

typealias TextViewLookup = (View) -> TextView

class HolderDelegateText private constructor(
    private val viewProvider: TextItemViewProvider,
    private val textViewLookup: TextViewLookup
) : HolderDelegate<TextViewHolder, TextContentData>() {

    @Suppress("unused")
    constructor(
        view: View,
        textViewLookup: TextViewLookup = { it as TextView }
    ) : this(ViewTextItemViewProvider(view), textViewLookup)

    constructor(
        @LayoutRes layoutId: Int,
        textViewLookup: TextViewLookup = { it as TextView }
    ) : this(LayoutIdTextItemViewProvider(layoutId), textViewLookup)

    override fun create(inflater: LayoutInflater, parent: ViewGroup): TextViewHolder {
        val itemView = viewProvider.get(inflater, parent)
        val textView = textViewLookup(itemView)
        return TextViewHolder(itemView, textView)
    }

    override fun bind(holder: TextViewHolder, position: Int, data: TextContentData) {
        holder.bind(data.text)
    }
}

class TextViewHolder(itemView: View, private val textView: TextView) :
    RecyclerView.ViewHolder(itemView) {

    fun bind(text: CharSequence) {
        textView.text = text
    }
}

private sealed class TextItemViewProvider {
    abstract fun get(inflater: LayoutInflater, parent: ViewGroup): View
}

private class ViewTextItemViewProvider(private val view: View) : TextItemViewProvider() {

    override fun get(inflater: LayoutInflater, parent: ViewGroup) = view

}

private class LayoutIdTextItemViewProvider(@LayoutRes private val layoutId: Int) :
    TextItemViewProvider() {

    override fun get(inflater: LayoutInflater, parent: ViewGroup): View =
        inflater.inflate(layoutId, parent, false)

}