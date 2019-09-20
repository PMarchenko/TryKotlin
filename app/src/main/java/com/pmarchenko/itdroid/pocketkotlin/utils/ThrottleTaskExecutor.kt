package com.pmarchenko.itdroid.pocketkotlin.utils

import android.os.Handler
import android.os.HandlerThread
import android.os.Message


/**
 * @author Pavel Marchenko
 */
class ThrottleTaskExecutor(
    private val tag: String = "ThrottleTaskExecutor",
    private val throttleTimeMillis: Long = 500L,
    private val maxPostponeOperations: Int = 10
) : Handler.Callback {

    private var handlerThread: HandlerThread = HandlerThread("$tag:HandlerThread")
    private val handler: Handler
    private var postponeCount: Int = 0

    init {
        if (maxPostponeOperations < 0) {
            throw IllegalArgumentException("maxPostponeOperations has to be >= 0, maxPostponeOperations=$maxPostponeOperations")
        }
        handlerThread.start()
        handler = Handler(handlerThread.looper, this as Handler.Callback)
    }

    fun throttle(operation: () -> Unit) {
        handler.removeMessages(WHAT_OPERATION_CANCELABLE)

        val what = if (postponeCount < maxPostponeOperations - 1) {
            postponeCount++
            WHAT_OPERATION_CANCELABLE
        } else {
            postponeCount = 0
            WHAT_OPERATION
        }
        logd(tag = tag, msg = "throttle: $what, count: $postponeCount")
        handler.sendMessageDelayed(handler.obtainMessage(what, operation), throttleTimeMillis)
    }

    override fun handleMessage(msg: Message?): Boolean {
        if (msg == null) return false
        return when (msg.what) {
            WHAT_OPERATION, WHAT_OPERATION_CANCELABLE -> {
                postponeCount = 0 //not critical to read/modify this variable from different threads
                logd(tag = tag, msg = "operation executed in thread: ${Thread.currentThread().name}")
                @Suppress("UNCHECKED_CAST")
                (msg.obj as () -> Unit).invoke()
                true
            }
            else -> false
        }
    }

    fun release() {
        //TODO BUG: last pending message can be canceled
        handlerThread.quitSafely()
        logd(tag = tag, msg = "release: $tag")
    }

    companion object {
        const val WHAT_OPERATION_CANCELABLE = 0
        const val WHAT_OPERATION = 1
    }
}