package com.pmarchenko.itdroid.pocketkotlin.model

/**
 * @author Pavel Marchenko
 */
abstract class KotlinProject(

    name: String,

    files: Map<String, ProjectFile>,

    var type: String = TYPE_RUN,

    var runConfig: String = CONFIG_JAVA,

    var searchForMain: Boolean,

    open val mainFileName: String

) : Project(name = name, files = files) {
    companion object {
        const val TYPE_RUN = "run"
        const val CONFIG_JAVA = "java"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as KotlinProject

        if (type != other.type) return false
        if (runConfig != other.runConfig) return false
        if (searchForMain != other.searchForMain) return false
        if (mainFileName != other.mainFileName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + runConfig.hashCode()
        result = 31 * result + searchForMain.hashCode()
        result = 31 * result + mainFileName.hashCode()
        return result
    }
}
