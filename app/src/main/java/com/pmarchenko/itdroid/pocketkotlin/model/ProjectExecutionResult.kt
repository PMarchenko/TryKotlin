package com.pmarchenko.itdroid.pocketkotlin.model

/**
 * @author Pavel Marchenko
 */
data class ProjectExecutionResult(
        val text: String?,
        val exception: ProjectException?,
        val errors: Map<String, ArrayList<ProjectError>>
) {

    fun hasErrors(fileName: String) = errors[fileName]?.isNotEmpty() ?: false

    fun hasErrors(): Boolean {
        for (errors in errors.values) {
            if (errors.isNotEmpty()) return true
        }
        return false
    }
}