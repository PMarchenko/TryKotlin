package com.pmarchenko.itdroid.pocketkotlin.compose

import com.pmarchenko.itdroid.pocketkotlin.projects.FileNameMaxLength
import com.pmarchenko.itdroid.pocketkotlin.projects.ProjectArgsMaxLength
import com.pmarchenko.itdroid.pocketkotlin.projects.ProjectNameMaxLength

/**
 * @author Pavel Marchenko
 */
open class MaxLengthFilter<in T : CharSequence>(
    private val maxLength: Int = Int.MAX_VALUE,
    private val onValueChanged: (T) -> Unit,
) : (T) -> Unit {

    init {
        if (maxLength < 1) error("Max length is 0 or negative: $maxLength")
    }

    override fun invoke(text: T) {
        @Suppress("UNCHECKED_CAST")
        onValueChanged(
            if (text.length <= maxLength) {
                text
            } else {
                text.substring(0, maxLength)
            } as T
        )
    }
}

class ProjectNameFilter(onValueChanged: (String) -> Unit) :
    MaxLengthFilter<String>(ProjectNameMaxLength, onValueChanged)

class FileNameFilter(onValueChanged: (String) -> Unit) :
    MaxLengthFilter<String>(FileNameMaxLength, onValueChanged)

class ProjectArgsFilter(onValueChanged: (String) -> Unit) :
    MaxLengthFilter<String>(ProjectArgsMaxLength, onValueChanged)