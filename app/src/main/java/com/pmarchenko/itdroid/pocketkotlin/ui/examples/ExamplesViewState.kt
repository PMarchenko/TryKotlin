package com.pmarchenko.itdroid.pocketkotlin.ui.examples

import com.pmarchenko.itdroid.pocketkotlin.projects.model.Example

/**
 * @author Pavel Marchenko
 */
data class ExamplesViewState(
    val isLoading: Boolean = false,
    val examples: List<Example> = emptyList()
)