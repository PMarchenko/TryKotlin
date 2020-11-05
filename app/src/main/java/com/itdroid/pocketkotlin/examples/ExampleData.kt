package com.itdroid.pocketkotlin.examples

import com.itdroid.pocketkotlin.projects.model.Example
import com.itdroid.pocketkotlin.utils.TextInput

/**
 * @author itdroid
 */
sealed class ExampleData

data class CategoryItemData(val name: TextInput) : ExampleData()

data class ExampleItemData(val index: Int, val example: Example) : ExampleData()
