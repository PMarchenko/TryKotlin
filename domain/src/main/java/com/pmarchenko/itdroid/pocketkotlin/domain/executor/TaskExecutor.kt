package com.pmarchenko.itdroid.pocketkotlin.domain.executor


/**
 * @author Pavel Marchenko
 */
interface TaskExecutor {

    fun execute(operation: () -> Unit)

    fun release()
}