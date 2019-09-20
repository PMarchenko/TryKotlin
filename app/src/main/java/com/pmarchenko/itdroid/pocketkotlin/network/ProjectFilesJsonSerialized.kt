package com.pmarchenko.itdroid.pocketkotlin.network

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectFile

/**
 * @author Pavel Marchenko
 */
class ProjectFilesJsonSerialized : TypeAdapter<MutableList<ProjectFile>>() {
    override fun write(out: JsonWriter?, value: MutableList<ProjectFile>?) {
        out?.apply {
            beginArray()
            value.orEmpty().forEach { file ->
                beginObject()
                name("publicId").value(file.publicId)
                name("name").value(file.name)
                name("text").value(file.program)
                endObject()
            }
            endArray()
        }
    }

    override fun read(`in`: JsonReader?): MutableList<ProjectFile> {
        TODO("not implemented")
    }
}