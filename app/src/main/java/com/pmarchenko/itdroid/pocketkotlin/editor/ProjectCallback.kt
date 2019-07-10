package com.pmarchenko.itdroid.pocketkotlin.editor

import com.pmarchenko.itdroid.pocketkotlin.model.ProjectError
import com.pmarchenko.itdroid.pocketkotlin.model.ProjectFile

/**
 * @author Pavel Marchenko
 */
interface ProjectCallback {

    fun showErrorDetails(fileName: String, line: Int, errors: ArrayList<ProjectError>)

    fun editProjectFile(file: ProjectFile, text: String)

    fun openProjectFile(fileName: String, line: Int, linePosition: Int)
}