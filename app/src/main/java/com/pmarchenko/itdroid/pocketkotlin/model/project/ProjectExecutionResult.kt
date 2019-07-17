package com.pmarchenko.itdroid.pocketkotlin.model.project

/**
 * @author Pavel Marchenko
 */
data class ProjectExecutionResult(
        val text: String?,
        val exception: ProjectException?,
        val errors: Map<String, ArrayList<ProjectError>>
) {

    fun hasErrors() = errors.any { it.value.isNotEmpty() }

}