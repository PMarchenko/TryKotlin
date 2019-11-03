package com.pmarchenko.itdroid.pocketkotlin.ui.examples.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.Example
import com.pmarchenko.itdroid.pocketkotlin.domain.extentions.bindView
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.ProjectCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.HolderDelegate

/**
 * @author Pavel Marchenko
 */
class HolderDelegateExample(private val callback: ProjectCallback) :
    HolderDelegate<HolderDelegateExample.ExampleViewHolder, ExampleContentData>() {

    override fun create(inflater: LayoutInflater, parent: ViewGroup): ExampleViewHolder {
        return ExampleViewHolder(
            inflater.inflate(R.layout.viewholder_example, parent, false),
            callback
        )
    }

    override fun bind(holder: ExampleViewHolder, position: Int, contentData: ExampleContentData) {
        holder.bind(contentData.example)
    }

    class ExampleViewHolder(
        itemView: View,
        private val projectListCallback: ProjectCallback
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val nameView by bindView<TextView>(R.id.name)

        private val descriptionView by bindView<TextView>(R.id.description)

        private var example: Example? = null

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(example: Example) {
            this.example = example

            nameView.text =
                example.modifiedProject?.name ?: error("Example ${example.id} is missing title")
            descriptionView.text = example.description
        }

        override fun onClick(v: View?) {
            example?.modifiedProject?.let {
                when (v) {
                    itemView -> projectListCallback.onProjectClick(it)
                }
            }
        }
    }
}