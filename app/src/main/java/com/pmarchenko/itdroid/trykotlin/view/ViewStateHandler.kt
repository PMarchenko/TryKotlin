package com.pmarchenko.itdroid.trykotlin.view

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
interface ViewStateHandler<S : State> {

    fun onNewViewState(state: S)
}