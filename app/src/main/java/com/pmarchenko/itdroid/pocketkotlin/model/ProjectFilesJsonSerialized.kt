package com.pmarchenko.itdroid.pocketkotlin.model

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

/**
 * @author Pavel Marchenko
 */
class ProjectFilesJsonSerialized : TypeAdapter<Map<String, ProjectFile>>() {
    override fun write(out: JsonWriter?, value: Map<String, ProjectFile>?) {
        out?.apply {
            beginArray()
            value.orEmpty().values.forEach { file ->
                beginObject()
                name("publicId").value(file.publicId)
                name("name").value(file.name)
                name("text").value(file.text)
                endObject()
            }
            endArray()
        }
    }

    override fun read(`in`: JsonReader?): Map<String, ProjectFile> {
        TODO("not implemented")
    }
}