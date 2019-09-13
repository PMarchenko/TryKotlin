package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import com.pmarchenko.itdroid.pocketkotlin.model.EditorError
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectFile

/**
 * @author Pavel Marchenko
 */
interface EditorCallback {

    fun showErrorDetails(file: ProjectFile, line: Int, errors: ArrayList<EditorError>)

    fun openFile(fileName: String, line: Int, linePosition: Int)

    fun onFileEdited(file: ProjectFile, text: String)
}