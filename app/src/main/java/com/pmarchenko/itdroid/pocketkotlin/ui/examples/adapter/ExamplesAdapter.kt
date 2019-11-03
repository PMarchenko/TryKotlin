package com.pmarchenko.itdroid.pocketkotlin.ui.examples.adapter

import android.widget.TextView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.Example
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.ProjectCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.*

/**
 * @author Pavel Marchenko
 */
class ExamplesAdapter(private val projectListCallback: ProjectCallback) : ContentAdapter() {

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

    override fun delegates(): Map<Int, HolderDelegate<*, *>> =
        mapOf(
            VIEW_TYPE_CATEGORY to HolderDelegateText(R.layout.viewholder_example_category) { it as TextView },
            VIEW_TYPE_EXAMPLE to HolderDelegateExample(projectListCallback)
        )

    companion object {
        const val VIEW_TYPE_CATEGORY = 0
        const val VIEW_TYPE_EXAMPLE = 1
    }
}