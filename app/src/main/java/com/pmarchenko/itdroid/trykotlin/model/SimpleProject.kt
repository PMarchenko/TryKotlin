package com.pmarchenko.itdroid.trykotlin.model

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
class SimpleProject : Project(
    searchForMain = true,
    mainFileName = MAIN_FILE_NAME,
    files = mapOf(MAIN_FILE_NAME to ProjectFile(publicId = PUBLIC_ID, name = MAIN_FILE_NAME, text = ""))
) {

    companion object {
        const val PUBLIC_ID = "SimpleProject"
        const val MAIN_FILE_NAME = "Simple.kt"
    }

    fun setCode(code: String) {
        (files[mainFileName] ?: error("No main file")).text = code
    }
}