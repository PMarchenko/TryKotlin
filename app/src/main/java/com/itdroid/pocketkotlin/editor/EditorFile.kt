package com.itdroid.pocketkotlin.editor

import android.text.Editable
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.projects.model.Project
import com.itdroid.pocketkotlin.projects.model.ProjectFile
import com.itdroid.pocketkotlin.projects.projectExamples
import com.itdroid.pocketkotlin.syntax.SyntaxViewModel
import com.itdroid.pocketkotlin.utils.rangeEnd
import com.itdroid.pocketkotlin.utils.rangeStart

/**
 * @author itdroid
 */
@Composable
fun EditorFile(
    project: Project,
    file: ProjectFile,
) {
    val vm = viewModel<EditorViewModel>()
    val selections = remember { mutableMapOf<Long, Long>() }
    val selectionChangeAction: (ProjectFile, Long) -> Unit = { key, sel ->
        selections[key.id] = sel
    }

    val syntaxVm = viewModel<SyntaxViewModel>()

    val editAction: (Editable) -> Unit = { input ->
        syntaxVm.highlightSyntax(input, false)

        val program = input.toString()
        if (file.program != program) {
            file.program = program
            vm.edit(project, file)
        }
    }

    EditorFileContentUi(
        file = file,
        selection = selections[file.id] ?: 0L,
        selectionChangeAction = selectionChangeAction,
        editAction = editAction
    )
}

@Composable
private fun EditorFileContentUi(
    file: ProjectFile,
    selection: Long,
    selectionChangeAction: (ProjectFile, Long) -> Unit,
    editAction: (Editable) -> Unit,
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
        editor.setSelection(selection.rangeStart(), selection.rangeEnd())
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
            editAction = {},
            selection = 0L,
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
            editAction = {},
            selection = 0L,
        )
    }
}
