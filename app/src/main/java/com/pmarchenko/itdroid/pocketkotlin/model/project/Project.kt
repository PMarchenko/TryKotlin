package com.pmarchenko.itdroid.pocketkotlin.model.project

import com.google.gson.annotations.JsonAdapter
import com.pmarchenko.itdroid.pocketkotlin.repository.ProjectFilesJsonSerialized

/**
 * @author Pavel Marchenko
 */
abstract class Project(

        val name: String,

        var args: String = "",

        val projectType: ProjectType,

        @JsonAdapter(ProjectFilesJsonSerialized::class)
    open val files: Map<String, ProjectFile>
) {
    abstract fun isValid(): Boolean
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Project

        if (name != other.name) return false
        if (args != other.args) return false
        if (files != other.files) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + args.hashCode()
        result = 31 * result + files.hashCode()
        return result
    }


}