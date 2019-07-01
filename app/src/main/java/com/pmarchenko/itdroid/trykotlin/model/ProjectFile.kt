package com.pmarchenko.itdroid.trykotlin.model

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
data class ProjectFile(
    val publicId: String,
    val name: String,
    var text: String
)