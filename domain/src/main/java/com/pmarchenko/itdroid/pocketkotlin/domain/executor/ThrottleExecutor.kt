package com.pmarchenko.itdroid.pocketkotlin.domain.executor

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicReference

/**
 * @author Pavel Marchenko
 */
class ThrottleExecutor private constructor(
    private val scope: CoroutineScope,
    private val throttleDelay: Long = 500L
) {

    private val activeTask = AtomicReference<Deferred<*>?>(null)

    fun execute(block: suspend () -> Unit) {
        scope.launch(Dispatchers.IO) {
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

        fun forScope(scope: CoroutineScope): ThrottleExecutor = ThrottleExecutor(scope)
    }
}