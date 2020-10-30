package com.pmarchenko.itdroid.pocketkotlin.dialog

import androidx.compose.ui.text.SoftwareKeyboardController

/**
 * @author Pavel Marchenko
 */
class KeyboardDismissCallbackWrapper(
    private val dismissAction: () -> Unit,
) : () -> Unit, (SoftwareKeyboardController) -> Unit {

    private var keyboardController: SoftwareKeyboardController? = null

    override fun invoke() {
        keyboardController?.hideSoftwareKeyboard()
        dismissAction()
    }

    override fun invoke(keyboardController: SoftwareKeyboardController) {
        this.keyboardController = keyboardController
    }
}