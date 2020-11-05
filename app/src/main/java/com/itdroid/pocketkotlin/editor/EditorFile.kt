package com.itdroid.pocketkotlin.editor

import android.text.Editable
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import com.itdroid.pocketkotlin.preferences.AppThemePreference
import com.itdroid.pocketkotlin.projects.model.Project
import com.itdroid.pocketkotlin.projects.model.ProjectFile
import com.itdroid.pocketkotlin.projects.projectExamples
import com.itdroid.pocketkotlin.syntax.SyntaxColorConfig
import com.itdroid.pocketkotlin.syntax.SyntaxViewModel
import com.itdroid.pocketkotlin.ui.compose.PocketKotlinTheme
import com.itdroid.pocketkotlin.ui.compose.syntaxColorConfig

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

    val syntaxColors: SyntaxColorConfig = MaterialTheme.colors.syntaxColorConfig
    val syntaxVm = viewModel<SyntaxViewModel>()
    val program: MutableState<CharSequence> = savedInstanceState(file.id) { file.program }

    val editAction: (Editable) -> Unit = { input ->
        syntaxVm.highlightSyntax(file.id, input, syntaxColors)
        program.value = input

        val update = input.toString()
        if (file.program != update) {
            file.program = update
            vm.edit(project, file)
        }
    }

    EditorFileContentUi(
        file = file,
        program = program.value,
        selection = selections[file.id] ?: 0..0,
        selectionChangeAction = selectionChangeAction,
        editAction = editAction
    )
}

@Composable
private fun EditorFileContentUi(
    file: ProjectFile,
    program: CharSequence,
    selection: IntRange,
    selectionChangeAction: (ProjectFile, IntRange) -> Unit,
    editAction: (Editable) -> Unit,
) {
    val editorTextColor = MaterialTheme.colors.onSurface.toArgb()
    val editorProvider = remember {
        ProgramEditorViewProvider(editorTextColor)
    }

    editorProvider.editAction = editAction

    AndroidView(editorProvider) { editor ->
        editor.reset()
        if (program !== editor.text) {
            editor.text = program
        }
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
            program = projectExamples[0].files[0].program,
            selectionChangeAction = { _, _ -> },
            editAction = {},
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
            program = projectExamples[0].files[0].program,
            selectionChangeAction = { _, _ -> },
            editAction = {},
            selection = 0..0,
        )
    }
}
