package com.pmarchenko.itdroid.pocketkotlin.ui.examples.adapter

import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.projects.model.Example
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.ProjectCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.*

/**
 * @author Pavel Marchenko
 */
class ExamplesAdapter(projectListCallback: ProjectCallback) : ContentAdapter() {

    override val delegates: Map<Int, HolderDelegate<out RecyclerView.ViewHolder, out ContentData>> =
        mapOf(
            VIEW_TYPE_CATEGORY to HolderDelegateText(R.layout.viewholder_example_category),
            VIEW_TYPE_EXAMPLE to HolderDelegateExample(projectListCallback)
        )

    fun setExamples(examples: List<Example>) {
        val content = mutableListOf<ContentData>()

        var lastCategory = ""
        for (example in examples) {
            val category = example.category
            if (lastCategory != category) {
                lastCategory = category
                content.add(TextContentData(VIEW_TYPE_CATEGORY, category))
            }
            content.add(ExampleContentData(VIEW_TYPE_EXAMPLE, example))
        }
        setContent(content)
    }

    companion object {
        private const val VIEW_TYPE_CATEGORY = 0
        private const val VIEW_TYPE_EXAMPLE = 1
    }
}