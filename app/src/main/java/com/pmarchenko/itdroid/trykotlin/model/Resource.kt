package com.pmarchenko.itdroid.trykotlin.model

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
interface Resource<out T>

abstract class ConsumableResource<out T> : Resource<T> {

    var consumed: Boolean = false

}

data class Success<out T>(val data: T?) : Resource<T>

class Loading<out T> : Resource<T>

data class Error<out T>(val message: String) : ConsumableResource<T>()