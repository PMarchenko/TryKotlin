package com.pmarchenko.itdroid.pocketkotlin.projects.model

/**
 * @author Pavel Marchenko
 */
sealed class Resource<out T>

data class Success<out T>(val data: T) : Resource<T>()

class Loading<out T> : Resource<T>()

data class Error<out T>(val message: String) : Resource<T>()
