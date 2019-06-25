package com.pmarchenko.itdroid.trykotlin.view

import androidx.core.util.ObjectsCompat

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
abstract class View<S : State>(private val stateHandler: ViewStateHandler<S>) {

    private var currentState: S? = null

    fun handleNewState(state: S) {
        if (!ObjectsCompat.equals(currentState, state)) {
            currentState = state
            stateHandler.onNewViewState(state)
        }
    }
}