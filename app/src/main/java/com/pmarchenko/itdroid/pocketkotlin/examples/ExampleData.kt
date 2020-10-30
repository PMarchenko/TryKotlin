package com.pmarchenko.itdroid.pocketkotlin.examples

import com.pmarchenko.itdroid.pocketkotlin.projects.model.Example
import com.pmarchenko.itdroid.pocketkotlin.utils.TextInput

/**
 * @author Pavel Marchenko
 */
sealed class ExampleData

data class CategoryItemData(val name: TextInput) : ExampleData()

data class ExampleItemData(val index: Int, val example: Example) : ExampleData()
