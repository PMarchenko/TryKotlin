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

        fun show(fragment: Fragment, title: CharSequence? = null, message: CharSequence) {
            val dialog = TextDialog()
            dialog.arguments = Bundle(2).apply {
                putCharSequence("title", title)
                putCharSequence("message", message)
            }
            dialog.setTargetFragment(fragment, 0)
            dialog.show(fragment.requireFragmentManager(), TAG)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(arguments?.getCharSequence("title"))
            .setMessage(arguments?.getCharSequence("message"))
            .setPositiveButton(R.string.dialog__text__positive_button, null)
            .create()
    }
}