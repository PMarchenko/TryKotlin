package com.pmarchenko.itdroid.trykotlin.model

import com.google.gson.annotations.JsonAdapter

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
abstract class Project(

    var type: String = TYPE_RUN,

    var runConfig: String = CONFIG_JAVA,

    var args: String = "",

    var searchForMain: Boolean,

    open val mainFileName: String,

    @JsonAdapter(ProjectFilesJsonSerialized::class)
    open val files: Map<String, ProjectFile>
) {
    companion object {
        const val TYPE_RUN = "run"
        const val CONFIG_JAVA = "java"
    }
}
