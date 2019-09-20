package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import com.pmarchenko.itdroid.pocketkotlin.model.EditorError
import com.pmarchenko.itdroid.pocketkotlin.model.project.Project
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.ProjectNameCallback

/**
 * @author Pavel Marchenko
 */
interface EditorCallback: ProjectNameCallback {

    fun showErrorDetails(file: ProjectFile, line: Int, errors: ArrayList<EditorError>)

    fun openFile(fileName: String, line: Int, linePosition: Int)

    fun onFileEdited(project: Project, file: ProjectFile, program: String)

}