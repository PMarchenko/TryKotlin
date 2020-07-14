package com.pmarchenko.itdroid.pocketkotlin.ui.examples.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.databinding.ViewholderExampleBinding
import com.pmarchenko.itdroid.pocketkotlin.projects.model.Example
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.ProjectCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.HolderDelegate
import kotlinx.android.extensions.LayoutContainer

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

    override fun bind(holder: ExampleViewHolder, position: Int, data: ExampleContentData) {
        holder.bind(data.example)
    }

    class ExampleViewHolder(
        override val containerView: View,
        private val projectListCallback: ProjectCallback
    ) : RecyclerView.ViewHolder(containerView), View.OnClickListener, LayoutContainer {

        private val binding = ViewholderExampleBinding.bind(containerView)
        private var example: Example? = null

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(example: Example) {
            this.example = example

            binding.name.text = example.modifiedProject.name
            binding.description.text = example.description
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