package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.ui.fromArgs
import com.pmarchenko.itdroid.pocketkotlin.ui.withArgs

/**
 * @author Pavel Marchenko
 */
class TextDialog : DialogFragment() {

    private var title: CharSequence? = null
    private lateinit var message: CharSequence

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = fromArgs(ARG_TITLE) { null }
        message = fromArgs(ARG_TEXT)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(R.string.dialog__text__positive_button, null)
            .create()

    companion object {

        private const val TAG = "TextDialog"
        private const val ARG_TITLE = "$TAG.TITLE"
        private const val ARG_TEXT = "$TAG.TEXT"

        fun show(fm: FragmentManager, title: CharSequence? = null, message: CharSequence) {
            TextDialog().withArgs {
                putCharSequence(ARG_TEXT, title)
                putCharSequence(ARG_TEXT, message)
            }.show(fm, TAG)
        }
    }
}