package com.pmarchenko.itdroid.pocketkotlin.dialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

/**
 * @author Pavel Marchenko
 */
class DialogViewModel(
    private val stateHandle: SavedStateHandle,
) : ViewModel() {

    val currentDialog: LiveData<Dialog> =
        stateHandle.getLiveData(STATE_KEY_DIALOG, NoneAppDialog)

    fun show(dialog: Dialog) {
        stateHandle.set(STATE_KEY_DIALOG, dialog)
    }

    fun dismiss() {
        show(NoneAppDialog)
    }

    companion object {

        private const val STATE_KEY_DIALOG = "DIALOG"
    }
}

