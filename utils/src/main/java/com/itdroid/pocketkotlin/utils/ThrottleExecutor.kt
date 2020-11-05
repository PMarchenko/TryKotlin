package com.itdroid.pocketkotlin.utils

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicReference

/**
 * @author itdroid
 */
class ThrottleExecutor private constructor(
    private val scope: CoroutineScope,
    private val throttleDelay: Long,
) {

    private val activeTask = AtomicReference<Deferred<*>?>(null)

    fun post(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        block: suspend () -> Unit,
    ) {
        scope.launch(dispatcher) {
            activeTask.get()?.cancelAndJoin()
            coroutineScope {
                val newTask = async(start = CoroutineStart.LAZY) {
                    delay(throttleDelay)
                    block()
                }
                newTask.invokeOnCompletion {
                    activeTask.compareAndSet(newTask, null)
                }
                while (true) {
                    if (!activeTask.compareAndSet(null, newTask)) {
                        activeTask.get()?.cancelAndJoin()
                        yield()
                    } else {
                        newTask.await()
                        break
                    }
                }
            }
        }
    }

    companion object Factory {

        fun forScope(
            scope: CoroutineScope,
            throttleDelay: Long = 500L,
        ): ThrottleExecutor = ThrottleExecutor(scope, throttleDelay)
    }
}