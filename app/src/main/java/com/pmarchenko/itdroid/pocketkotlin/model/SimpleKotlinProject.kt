package com.pmarchenko.itdroid.pocketkotlin.model

/**
 * @author Pavel Marchenko
 */
open class SimpleKotlinProject(name: String,
                               file: String = MAIN_FILE_NAME,
                               publicId: String = PUBLIC_ID) : KotlinProject(
        name = name,
        searchForMain = true,
        mainFileName = file,
        files = mapOf(file to ProjectFile(publicId = publicId, name = file, text = ""))
) {

    companion object {
        const val PUBLIC_ID = "SimpleKotlinProject"
        const val MAIN_FILE_NAME = "Simple.kt"
    }

    fun setCode(code: String) {
        (files[mainFileName] ?: error("No main file")).text = code
    }

    override fun isValid(): Boolean {
        //todo validation
        return files.isNotEmpty()
    }
}