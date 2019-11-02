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
class HolderDelegateText private constructor(
    private val view: View?,
    @LayoutRes private val layoutId: Int,
    private val textViewProvider: (itemView: View) -> TextView
) :
    HolderDelegate<HolderDelegateText.TextViewHolder, TextContentData>() {

    constructor(view: View, textViewProvider: (itemView: View) -> TextView) : this(view, NO_LAYOUT_RES, textViewProvider)

    constructor(@LayoutRes layoutId: Int, textViewProvider: (itemView: View) -> TextView) : this(null, layoutId, textViewProvider)

    override fun create(inflater: LayoutInflater, parent: ViewGroup): TextViewHolder {
        val view = when {
            view != null -> view
            layoutId != NO_LAYOUT_RES -> inflater.inflate(layoutId, parent, false)
            else -> error("No empty view provided")
        }
        val textView = textViewProvider(view)
        return TextViewHolder(view, textView)
    }

    override fun bind(holder: TextViewHolder, position: Int, contentData: TextContentData) {
        holder.bind(contentData.text)
    }

    companion object {
        const val NO_LAYOUT_RES = -1
    }

    class TextViewHolder(
        itemView: View,
        private val textView: TextView
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(text: CharSequence) {
            textView.text = text
        }
    }
}