package com.itdroid.pocketkotlin.dialog

import androidx.compose.ui.text.SoftwareKeyboardController

/**
 * @author itdroid
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