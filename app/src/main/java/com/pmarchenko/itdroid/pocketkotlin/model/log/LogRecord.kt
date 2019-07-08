package com.pmarchenko.itdroid.pocketkotlin.model.log

import java.util.*

/**
 * @author Pavel Marchenko
 */
open class LogRecord(
        val timestamp: Long = System.currentTimeMillis(),
        val timestampNano: Long = System.nanoTime()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LogRecord

        return timestamp == other.timestamp && timestampNano == other.timestampNano
    }

    override fun hashCode(): Int {
        return Objects.hash(timestamp, timestampNano)
    }
}