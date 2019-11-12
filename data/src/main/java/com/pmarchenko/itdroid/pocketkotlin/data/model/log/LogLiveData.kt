package com.pmarchenko.itdroid.pocketkotlin.data.model.log

import androidx.lifecycle.MutableLiveData

/**
 * @author Pavel Marchenko
 */
class LogLiveData : MutableLiveData<List<LogRecord>>() {

    companion object {
        const val MAX_LOG_SIZE = 100
    }

    fun setValue(log: LogRecord) {
        super.setValue(mergeLog(log))
    }

    fun postValue(log: LogRecord) {
        super.postValue(mergeLog(log))
    }

    private fun mergeLog(log: LogRecord): List<LogRecord> {
        val logs = value
        val out =
            if (logs == null) {
                mutableListOf<LogRecord>()
            } else {
                ArrayList(logs)
            }
        out.add(log)
        return out.takeLast(MAX_LOG_SIZE)
    }

    fun clearLogs() {
        super.setValue(emptyList())
    }
}