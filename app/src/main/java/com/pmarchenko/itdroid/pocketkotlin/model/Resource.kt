package com.pmarchenko.itdroid.pocketkotlin.model

/**
 * @author Pavel Marchenko
 */
interface Resource<out T>

data class Success<out T>(val data: T) : Resource<T>

class Loading<out T> : Resource<T>

data class Error<out T>(val message: String) : Resource<T>