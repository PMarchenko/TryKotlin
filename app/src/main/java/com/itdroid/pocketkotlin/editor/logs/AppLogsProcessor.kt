package com.itdroid.pocketkotlin.editor.logs

/**
 * @author itdroid
 */
interface AppLogsProcessor<T> {

    fun process(log: LogRecord) : T
}
