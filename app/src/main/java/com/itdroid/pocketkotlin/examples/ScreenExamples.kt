package com.itdroid.pocketkotlin.examples

import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.navigation.ProjectEditorDestination
import com.itdroid.pocketkotlin.navigation.navigation
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.projects.exampleExamples
import com.itdroid.pocketkotlin.projects.model.Example
import com.itdroid.pocketkotlin.ui.compose.*
import com.itdroid.pocketkotlin.ui.compose.state.UiState
import com.itdroid.pocketkotlin.ui.compose.state.defaultUiState
import com.itdroid.pocketkotlin.utils.TextInput

/**
 * @author itdroid
 */
@Composable
fun ScreenExamples() {
    Box(Modifier.fillMaxSize()) {
        val uiState by viewModel<ExamplesViewModel>().examplesState
            .observeAsState(defaultUiState())

        val nav = navigation()
        val openExampleAction: (Example) -> Unit = { example ->
            nav.navigateTo(ProjectEditorDestination(example.modifiedProject.id))
        }

        ScreenExamplesInput(
            uiState = uiState,
            openExampleAction = openExampleAction,
        )
    }
}

@Composable
private fun ScreenExamplesInput(
    uiState: UiState<List<ExampleData>>,
    openExampleAction: (Example) -> Unit,
) {

    uiState.consume { examples ->
        LazyColumnFor(examples) { item ->
            when (item) {
                is CategoryItemData -> CategoryItem(item)
                is ExampleItemData -> ExampleItem(item, openExampleAction)
            }
        }
    }
}

@Composable
private fun CategoryItem(item: CategoryItemData) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        ListSection(text = item.name)
        AppDivider()
    }
}

@Composable
private fun ExampleItem(
    item: ExampleItemData,
    openExampleAction: (Example) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                openExampleAction(item.example)
            }
            .padding(vertical = 16.dp)
    ) {
        Spacer(Modifier.width(68.dp))

        Text("${item.index + 1}.")

        Spacer(Modifier.width(8.dp))

        Text(item.example.exampleProject.name)
    }
}

@Preview("ScreenExamples preview [Light Theme]")
@Composable
private fun ExamplesScreenPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        ScreenExamplesInput(
            uiState = UiState(examplesData()),
            openExampleAction = {}
        )
    }
}

@Preview("ScreenExamples preview [Dark Theme]")
@Composable
private fun ExamplesScreenPreview() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        ScreenExamplesInput(
            uiState = UiState(examplesData()),
            openExampleAction = {}
        )
    }
}

private fun examplesData() = ArrayList<ExampleData>().apply {
    repeat(3) {
        add(CategoryItemData(TextInput(text = "Category $it")))
        exampleExamples.mapIndexedTo(this) { index, example ->
            ExampleItemData(index, example)
        }
    }
}
