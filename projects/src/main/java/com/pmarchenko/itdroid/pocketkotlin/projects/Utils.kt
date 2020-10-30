package com.pmarchenko.itdroid.pocketkotlin.projects

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

/**
 * @author Pavel Marchenko
 */

/**
 * It is Ok to use blocking call on [Dispatchers.IO]
 * */
@Suppress("BlockingMethodInNonBlockingContext")
suspend fun <T> Response<T>.errorString(): String? =
    withContext(Dispatchers.IO) {
        errorBody()?.string()
    }
