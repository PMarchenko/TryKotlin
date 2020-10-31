package com.itdroid.pocketkotlin.network.model

/**
 * @author itdroid
 */
data class Project(

    val id: String?,

    val name: String,

    val args: String,

    val confType: String,

    val originUrl: String?,

    val files: List<File>,

    val readOnlyFileNames: List<String>
)

data class File(
    val publicId: String,
    val name: String,
    val text: String
)