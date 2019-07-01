package com.pmarchenko.itdroid.trykotlin.model

import com.google.gson.Gson
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
class ProjectConverterFactory : Converter.Factory() {

    override fun stringConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<*, String>? =
        if (type == Project::class.java) {
            Converter<Project, String> { Gson().toJson(it) }
        } else {
            super.stringConverter(type, annotations, retrofit)
        }
}