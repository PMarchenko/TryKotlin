package com.itdroid.pocketkotlin.editor

import android.text.Editable
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.projects.model.Project
import com.itdroid.pocketkotlin.projects.model.ProjectFile
import com.itdroid.pocketkotlin.projects.projectExamples
import com.itdroid.pocketkotlin.syntax.SyntaxViewModel
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme

/**
 * @author itdroid
 */
@Composable
fun EditorFile(
    project: Project,
    file: ProjectFile,
) {
    val vm = viewModel<EditorViewModel>()
    val selections = remember { mutableMapOf<Long, IntRange>() }
    val selectionChangeAction: (ProjectFile, IntRange) -> Unit = { key, sel ->
        selections[key.id] = sel
    }

    val isLightTheme = MaterialTheme.colors.isLight
    val syntaxVm = viewModel<SyntaxViewModel>()

    val editAction: (Editable, IntRange) -> Unit = { input, range ->
        syntaxVm.highlightSyntax(file.id, input, range, isLightTheme)

        val program = input.toString()
        if (file.program != program) {
            file.program = program
            vm.edit(project, file)
        }
    }

    EditorFileContentUi(
        file = file,
        selection = selections[file.id] ?: 0..0,
        selectionChangeAction = selectionChangeAction,
        editAction = editAction
    )
}

@Composable
private fun EditorFileContentUi(
    file: ProjectFile,
    selection: IntRange,
    selectionChangeAction: (ProjectFile, IntRange) -> Unit,
    editAction: (Editable, IntRange) -> Unit,
) {
    val editorBgColor = MaterialTheme.colors.background.toArgb()
    val editorTextColor = MaterialTheme.colors.onSurface.toArgb()
    val editorProvider = remember {
        ProgramEditorViewProvider(
            bgColor = editorBgColor,
            textColor = editorTextColor
        )
    }

    editorProvider.editAction = editAction

    AndroidView(editorProvider) { editor ->
        editor.reset()
        editor.setText(file.program)
        editor.setSelection(selection.first, selection.last)
        editor.selectionListener = { range -> selectionChangeAction(file, range) }
    }
}

@Preview("EditorFileContent preview [Light Theme]")
@Composable
private fun EditorFileContentPreviewLightTheme() {
    PocketKotlinTheme(AppThemePreference.Light) {
        EditorFileContentUi(
            file = projectExamples[0].files[0],
            selectionChangeAction = { _, _ -> },
            editAction = { _, _ -> },
            selection = 0..0,
        )
    }
}

@Preview("EditorFileContent preview [Dark Theme]")
@Composable
private fun EditorFileContentPreviewDarkTheme() {
    PocketKotlinTheme(AppThemePreference.Dark) {
        EditorFileContentUi(
            file = projectExamples[0].files[0],
            selectionChangeAction = { _, _ -> },
            editAction = { _, _ -> },
            selection = 0..0,
        )
    }
}
