package com.itdroid.pocketkotlin.dialog

import android.os.Parcelable
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.viewModel
import com.itdroid.pocketkotlin.R
import com.itdroid.pocketkotlin.ui.compose.FileNameFilter
import com.itdroid.pocketkotlin.ui.compose.ProjectArgsFilter
import com.itdroid.pocketkotlin.ui.compose.ProjectNameFilter
import com.itdroid.pocketkotlin.dialog.ui.*
import com.itdroid.pocketkotlin.editor.EditorViewModel
import com.itdroid.pocketkotlin.navigation.Destination
import com.itdroid.pocketkotlin.navigation.navigation
import com.itdroid.pocketkotlin.project.projects
import com.itdroid.pocketkotlin.projects.model.FileType
import com.itdroid.pocketkotlin.projects.model.Project
import com.itdroid.pocketkotlin.projects.model.ProjectFile
import kotlinx.android.parcel.Parcelize

/**
 * @author itdroid
 */
sealed class Dialog : Parcelable {

    @Composable
    abstract fun show(dismissAction: () -> Unit)
}

@Parcelize
object NoneAppDialog : Dialog() {

    @Composable
    override fun show(dismissAction: () -> Unit) {
        // show nothing
    }
}

@Parcelize
object AddNewProjectDialog : Dialog() {

    @Composable
    override fun show(dismissAction: () -> Unit) {
        val projects = projects()
        val navigation = navigation()

        val defaultName = stringResource(R.string.dialog__add_project__default_name)
        val projectNameState = savedInstanceState { defaultName }
        val includeMainState = savedInstanceState { true }
        val isValidState = savedInstanceState { true }

        val nameChangeAction = ProjectNameFilter {
            projectNameState.value = it
            isValidState.value = true
        }

        val keyboardDismissAction = KeyboardDismissCallbackWrapper(dismissAction)
        val includeMainChangeAction: (Boolean) -> Unit = { includeMainState.value = it }
        val openProjectAction: (Destination) -> Unit = { navigation.navigateTo(it) }
        val addProjectAction: () -> Unit = {
            val name = projectNameState.value
            if (name.isEmpty()) {
                isValidState.value = false
            } else {
                projects.add(name, includeMainState.value, openProjectAction)
                keyboardDismissAction()
            }
        }

        AddNewProjectDialogUi(
            projectNameState = projectNameState,
            includeMainState = includeMainState,
            isValidState = isValidState,

            nameChangeAction = nameChangeAction,
            includeMainChangeAction = includeMainChangeAction,
            addProjectAction = addProjectAction,
            dismissAction = keyboardDismissAction,
            onTextInputStartedAction = keyboardDismissAction
        )
    }
}

@Parcelize
object ConfirmDeleteAllDialog : Dialog() {

    @Composable
    override fun show(dismissAction: () -> Unit) {
        val projects = projects()
        ConfirmDeleteAllDialogUi(
            dismissAction = dismissAction,
            deleteAllAction = { projects.cleanupTrashBin() }
        )
    }
}

@Parcelize
data class ChangeNameDialog(val project: Project) : Dialog() {

    @Composable
    override fun show(dismissAction: () -> Unit) {
        val projects = projects()
        var newName by savedInstanceState { project.name }
        var isValidProjectName by remember { mutableStateOf(false) }
        val keyboardDismissAction = KeyboardDismissCallbackWrapper(dismissAction)

        ChangeNameDialogUi(
            isValidProjectName = isValidProjectName,
            newName = newName,
            saveAction = {
                if (newName.isEmpty()) {
                    isValidProjectName = true
                } else {
                    projects.update(project.copy(name = newName))
                    keyboardDismissAction()
                }
            },
            nameChangeAction = ProjectNameFilter {
                newName = it
                isValidProjectName = false
            },
            dismissAction = keyboardDismissAction,
            onTextInputStartedAction = keyboardDismissAction
        )
    }
}

@Parcelize
data class CreateFileDialog(private val project: Project) : Dialog() {

    @Composable
    override fun show(dismissAction: () -> Unit) {
        val defaultName = stringResource(
            R.string.dialog__create_file__default_name,
            project.files.size + 1
        )
        var fileName by savedInstanceState { defaultName }
        var isValidFileName by savedInstanceState { true }
        var selectedFileType by savedInstanceState { FileType.File }

        val editor = viewModel<EditorViewModel>()
        val onFileTypeChanged: (FileType) -> Unit = { selectedFileType = it }
        val onNameChangeAction = FileNameFilter {
            fileName = it
            isValidFileName = true
        }
        val keyboardDismissAction = KeyboardDismissCallbackWrapper(dismissAction)
        val addAction = {
            if (fileName.isEmpty()) {
                isValidFileName = false
            } else {
                editor.addFile(project, fileName, selectedFileType) //open last one
                keyboardDismissAction()
            }
        }

        CreateFileDialogUi(
            fileName = fileName,
            isValidFileName = isValidFileName,
            selectedFileType = selectedFileType,
            onNameChangeAction = onNameChangeAction,
            onFileTypeChanged = onFileTypeChanged,
            addAction = addAction,
            dismissAction = keyboardDismissAction,
            onTextInputStarted = keyboardDismissAction
        )
    }
}

@Parcelize
data class DeleteFileDialog(private val file: ProjectFile) : Dialog() {

    @Composable
    override fun show(dismissAction: () -> Unit) {
        val projects = projects()
        DeleteFileDialogUi(
            fileName = file.name,
            deleteAction = {
                projects.delete(file)
                dismissAction()
            },
            dismissAction = dismissAction
        )
    }
}

@Parcelize
data class ProjectArgsDialog(private val project: Project) : Dialog() {

    @Composable
    override fun show(dismissAction: () -> Unit) {
        var args by savedInstanceState { project.args }
        val projects = projects()
        val keyboardDismissAction = KeyboardDismissCallbackWrapper(dismissAction)
        val saveAction = {
            projects.update(project.copy(args = args))
            keyboardDismissAction()
        }

        ProjectArgsDialogUi(
            args = args,
            onArgsChangeAction = ProjectArgsFilter { args = it },
            saveAction = saveAction,
            dismissAction = dismissAction,
            onTextInputStarted = keyboardDismissAction
        )
    }
}

@Parcelize
object EasterEggDialog : Dialog() {

    @Composable
    override fun show(dismissAction: () -> Unit) {
        EasterEggDialogUi(dismissAction)
    }
}