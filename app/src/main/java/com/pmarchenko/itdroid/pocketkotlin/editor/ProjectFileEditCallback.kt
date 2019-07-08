package com.pmarchenko.itdroid.pocketkotlin.editor

import com.pmarchenko.itdroid.pocketkotlin.model.ProjectFile

/**
 * @author Pavel Marchenko
 */
interface ProjectFileEditCallback {

    fun onProjectFileEdit(file: ProjectFile)
}