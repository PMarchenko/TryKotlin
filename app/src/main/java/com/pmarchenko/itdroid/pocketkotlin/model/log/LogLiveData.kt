package com.pmarchenko.itdroid.pocketkotlin.model.log

import androidx.lifecycle.MutableLiveData

/**
 * @author Pavel Marchenko
 */
class LogLiveData : MutableLiveData<List<LogRecord>>() {

    companion object {
        const val MAX_LOG_SIZE = 100
    }

    private val logs = mutableListOf<LogRecord>()

    fun setValue(log: LogRecord) {
        mergeLog(log)
        super.setValue(ArrayList(logs))
    }

    fun postValue(log: LogRecord) {
        mergeLog(log)
        super.postValue(ArrayList(logs))
    }

    override fun setValue(value: List<LogRecord>?) {
        saveLogs(value)
        super.setValue(value)
    }

    override fun postValue(value: List<LogRecord>?) {
        saveLogs(value)
        super.postValue(value)
    }

    private fun saveLogs(value: List<LogRecord>?) {
        logs.clear()
        if (value != null) logs.addAll(value.takeLast(100))
    }

    private fun mergeLog(log: LogRecord) {
        if (logs.size >= MAX_LOG_SIZE) {
            logs.removeAt(0)
        }
        logs.add(log)
    }
}