package com.itdroid.pocketkotlin.examples

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.projects.ProjectsRepository
import com.itdroid.pocketkotlin.projects.model.Example
import com.itdroid.pocketkotlin.ui.compose.state.UiState
import com.itdroid.pocketkotlin.utils.ErrorInput
import com.itdroid.pocketkotlin.utils.TextInput

/**
 * @author itdroid
 */
class ExamplesViewModel(app: Application) : AndroidViewModel(app) {

    private val projectRepo = ProjectsRepository.instance(app)

    val examplesState: LiveData<UiState<List<ExampleData>>> = projectRepo.examples
        .map(List<Example>::asExamplesData)
        .map { examples ->
            if (examples.isEmpty()) UiState(error = ErrorInput(R.string.screen__examples__empty))
            else UiState(examples)
        }
}

fun List<Example>.asExamplesData(): List<ExampleData> =
    mutableListOf<ExampleData>().also { out ->
        var currentCategory: String? = null
        var index = 0

        for (example in this) {
            if (currentCategory != example.category) {
                currentCategory = example.category
                index = 0
                out.add(CategoryItemData(TextInput(text = currentCategory)))
            }
            out.add(ExampleItemData(index++, example))
        }
    }