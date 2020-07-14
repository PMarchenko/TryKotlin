package com.pmarchenko.itdroid.pocketkotlin.ui

import androidx.lifecycle.Observer

/**
 * @author Pavel Marchenko
 */
class Event<T>(private val data: T) {

    var isConsumed: Boolean = false
        private set

    fun consume(consumer: (T) -> Unit): Boolean {
        return if (!isConsumed && data != null) {
            isConsumed = true
            consumer(data)
            true
        } else {
            false
        }
    }
}

class EventObserver<E>(private val consumer: (E) -> Unit) : Observer<Event<E>> {

    override fun onChanged(event: Event<E>?) {
        event?.consume { consumer(it) }
    }
}