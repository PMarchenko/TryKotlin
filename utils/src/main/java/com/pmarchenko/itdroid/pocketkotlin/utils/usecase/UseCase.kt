package com.pmarchenko.itdroid.pocketkotlin.utils.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author Pavel Marchenko
 */
abstract class UseCase<T> {

    protected abstract val params: T

    protected abstract suspend operator fun invoke(params: T)

    suspend fun execute() {
        withContext(Dispatchers.IO) {
            invoke(params)
        }
    }
}