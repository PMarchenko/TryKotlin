package com.pmarchenko.itdroid.pocketkotlin.domain.network

import com.google.gson.GsonBuilder
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.Project
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


/**
 * @author Pavel Marchenko
 */
class ProjectConverterFactory : Converter.Factory() {

    override fun stringConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? =
        if (type == Project::class.java) {
            Converter<Project, String> {
                GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create()
                    .toJson(it)
            }
        } else {
            super.stringConverter(type, annotations, retrofit)
        }
}