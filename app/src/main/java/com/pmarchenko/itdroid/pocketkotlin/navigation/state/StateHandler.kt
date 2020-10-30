package com.pmarchenko.itdroid.pocketkotlin.navigation.state

import androidx.lifecycle.SavedStateHandle

/**
 * @author Pavel Marchenko
 */
abstract class StateHandler(
    private val stateHandle: SavedStateHandle,
) {

    protected fun <T> restore(key: String): T? = stateHandle.get<T>(key)

    protected fun <T> save(key: String, data: T) {
        stateHandle.set(key, data)
    }
}
