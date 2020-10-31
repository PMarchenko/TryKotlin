package com.itdroid.pocketkotlin.preferences

import com.google.gson.Gson

/**
 * @author itdroid
 */
internal inline fun <reified T : Enum<T>> enumConverter(): Converter<T> =
    Converter(
        serializer = {
            Gson().toJson(it)
        },
        deserializer = { raw, opt ->
            Gson().fromJson(raw, T::class.java) ?: opt
        }
    )

