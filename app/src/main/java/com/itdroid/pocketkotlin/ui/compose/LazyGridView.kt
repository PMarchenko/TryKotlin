package com.itdroid.pocketkotlin.ui.compose

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.preferences.AppThemePreference

/**
 * @author itdroid
 */
@Composable
fun <T> LazyGridFor(
    items: List<T>,
    rowSize: Int = 1,
    itemContent: @Composable BoxScope.(T) -> Unit,
) {
    val rows = items.windowed(rowSize, rowSize, true)
    LazyColumnFor(rows) { row ->
        Row(Modifier.fillParentMaxWidth()) {
            for ((index, item) in row.withIndex()) {
                Box(Modifier.fillMaxWidth(1f / (rowSize - index))) {
                    itemContent(item)
                }
            }
        }
    }
}

@Preview("LazyGridFor preview [Light Theme]")
@Composable
private fun LazyGridForPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        val data = (1..100).map(Integer::toString)
        LazyGridFor(data, 3) { item ->
            Text(item)
        }
    }
}

@Preview("LazyGridFor preview [Dark Theme]")
@Composable
private fun LazyGridForPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        val data = (1..100).map(Integer::toString)
        LazyGridFor(data, 3) { item ->
            Text(item)
        }
    }
}
