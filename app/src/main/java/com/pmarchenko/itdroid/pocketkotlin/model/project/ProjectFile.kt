package com.pmarchenko.itdroid.pocketkotlin.model.project

/**
 * @author Pavel Marchenko
 */
data class ProjectFile(
    val publicId: String,
    val name: String,
    var text: String
)