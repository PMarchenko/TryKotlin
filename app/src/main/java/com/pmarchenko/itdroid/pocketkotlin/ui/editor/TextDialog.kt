package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.pmarchenko.itdroid.pocketkotlin.R

/**
 * @author Pavel Marchenko
 */
class TextDialog : DialogFragment() {

    companion object {

        private const val TAG = "TextDialog"

        private const val KEY_TITLE = "$TAG.TITLE"
        private const val KEY_TEXT = "$TAG.TEXT"

        fun show(fragment: Fragment, title: CharSequence? = null, message: CharSequence) {
            val dialog = TextDialog()
            dialog.arguments = Bundle(2).apply {
                putCharSequence(KEY_TITLE, title)
                putCharSequence(KEY_TEXT, message)
            }
            dialog.show(fragment.childFragmentManager, TAG)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(arguments?.getCharSequence(KEY_TITLE))
            .setMessage(arguments?.getCharSequence(KEY_TEXT))
            .setPositiveButton(R.string.dialog__text__positive_button, null)
            .create()

}