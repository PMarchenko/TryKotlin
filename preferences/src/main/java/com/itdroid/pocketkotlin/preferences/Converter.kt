package com.itdroid.pocketkotlin.preferences

/**
 * @author itdroid
 */
internal class Converter<T>(
    private val serializer: (T) -> String,
    private val deserializer: (String, T) -> T,
) {

    fun serialize(data: T): String = serializer(data)

    fun deserialize(a: String, opt: T): T = deserializer(a, opt)

}