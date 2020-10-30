package com.pmarchenko.itdroid.pocketkotlin.editor.logs

/**
 * @author Pavel Marchenko
 */
interface AppLogsProcessor<T> {

    fun process(log: LogRecord) : T
}
