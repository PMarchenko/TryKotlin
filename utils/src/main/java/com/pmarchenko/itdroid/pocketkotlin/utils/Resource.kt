package com.pmarchenko.itdroid.pocketkotlin.utils

/**
 * @author Pavel Marchenko
 */
sealed class Resource

object Loading : Resource()

data class SuccessResult<out T>(val data: T) : Resource()

data class FailureResult(
    val message: String,
    val responseCode: Int,
) : Resource()
