package com.itdroid.pocketkotlin.editor.logs

import androidx.lifecycle.MutableLiveData
import com.itdroid.pocketkotlin.utils.iLog
import com.itdroid.pocketkotlin.utils.safeCheckUiThread
import java.util.*


/**
 * @author itdroid
 */
class AppLogger<T>(
    private val logsProcessor: AppLogsProcessor<T>,
) : MutableLiveData<List<T>>(LinkedList()) {

    companion object {

        const val MAX_LOG_SIZE = 500
    }

    fun log(log: LogRecord) {
        log.log().process().record()
    }

    override fun postValue(value: List<T>?): Nothing =
        error("Use [log(LogRecord)] instead")

    override fun setValue(value: List<T>?): Nothing =
        error("Use [log(LogRecord)] instead")

    private fun LogRecord.process() = logsProcessor.process(this)

    private fun LogRecord.log() = this.also { iLog { it.toString() } }

    private fun T.record() {
        val logs = LinkedList(value!!)
        logs.addLast(this)
        if (logs.size > MAX_LOG_SIZE) {
            logs.removeFirst()
        }
        recordLogs(logs)
    }

    private fun recordLogs(logs: List<T>) {
        if (safeCheckUiThread()) {
            super.setValue(logs)
        } else {
            super.postValue(logs)
        }
    }

    fun clearLogs() {
        recordLogs(LinkedList())
    }
}
