package com.pmarchenko.itdroid.pocketkotlin.utils.executor


/**
 * @author Pavel Marchenko
 */
interface TaskExecutor {

    fun execute(operation: () -> Unit)

    fun release()
}