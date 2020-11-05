package com.itdroid.pocketkotlin.network

import com.google.gson.Gson
import com.itdroid.pocketkotlin.network.model.Project
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


/**
 * @author itdroid
 */
class ProjectConverterFactory : Converter.Factory() {

    override fun stringConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, String>? =
        if (type == Project::class.java) {
            Converter<Project, String> { Gson().toJson(it) }
        } else {
            super.stringConverter(type, annotations, retrofit)
        }
}