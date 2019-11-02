package com.pmarchenko.itdroid.pocketkotlin.model.project

/**
 * @author Pavel Marchenko
 */
data class ProjectExecutionResult(
    val text: String?,
    val exception: ProjectException?,
    val errors: MutableMap<String, ArrayList<ProjectError>>,
    val testResults: MutableMap<String, ArrayList<TestResult>>?
) {

    fun hasErrors() = errors.any { it.value.isNotEmpty() }

}