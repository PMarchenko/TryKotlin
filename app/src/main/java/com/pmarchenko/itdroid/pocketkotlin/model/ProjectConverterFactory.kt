package com.pmarchenko.itdroid.pocketkotlin.model

import com.google.gson.Gson
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * @author Pavel Marchenko
 */
class ProjectConverterFactory : Converter.Factory() {

    override fun stringConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<*, String>? =
        if (type == KotlinProject::class.java) {
            Converter<KotlinProject, String> { Gson().toJson(it) }
        } else {
            super.stringConverter(type, annotations, retrofit)
        }
}