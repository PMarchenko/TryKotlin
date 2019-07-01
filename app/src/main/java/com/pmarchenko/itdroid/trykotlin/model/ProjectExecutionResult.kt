package com.pmarchenko.itdroid.trykotlin.model

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
data class ProjectExecutionResult(
    val text: String,
    val exception: ProjectException,
    val errors: Map<String, Array<ProjectError>>
)