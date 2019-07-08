package com.pmarchenko.itdroid.pocketkotlin.model

/**
 * @author Pavel Marchenko
 */
open class SimpleKotlinProject(name: String) : KotlinProject(
    name = name,
    searchForMain = true,
    mainFileName = MAIN_FILE_NAME,
    files = mapOf(MAIN_FILE_NAME to ProjectFile(publicId = PUBLIC_ID, name = MAIN_FILE_NAME, text = ""))
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