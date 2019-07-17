package com.pmarchenko.itdroid.pocketkotlin.editor

import com.pmarchenko.itdroid.pocketkotlin.model.EditorError
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectError
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectFile

/**
 * @author Pavel Marchenko
 */
interface ProjectCallback {

    fun showErrorDetails(file: ProjectFile, line: Int, errors: ArrayList<EditorError>)

    fun editProjectFile(file: ProjectFile, text: String)

    fun openProjectFile(fileName: String, line: Int, linePosition: Int)
}