package com.pmarchenko.itdroid.pocketkotlin.utils.executor

/**
 * @author Pavel Marchenko
 */
class ImmediateTaskExecutor : TaskExecutor {
    override fun execute(operation: () -> Unit) {
        operation()
    }

    override fun release() {
        //no-op
    }
}