package com.pmarchenko.itdroid.pocketkotlin.editor

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.pmarchenko.itdroid.pocketkotlin.R

/**
 * @author Pavel Marchenko
 */
class CommandLineArgsDialog : DialogFragment(), DialogInterface.OnClickListener {

    companion object {

        private const val TAG = "CommandLineArgsDialog"

        fun show(fragment: Fragment, args: String) {
            val dialog = CommandLineArgsDialog()
            dialog.arguments = Bundle(1).apply {
                putString("args", args)
            }
            dialog.setTargetFragment(fragment, 0)
            dialog.show(fragment.requireFragmentManager(), TAG)
        }
    }

    private lateinit var editText: EditText
    private lateinit var callback: CommandLineArgsDialogCallback
    private var clArgs: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = targetFragment as CommandLineArgsDialogCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            clArgs = arguments?.getString("args")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
                .setTitle(R.string.dialog_command_line_args_title)
                .setView(R.layout.dialog_command_line_args)
                .setNegativeButton(R.string.dialog_command_line_args_negative_button, null)
                .setPositiveButton(R.string.dialog_command_line_args_positive_button, this)
                .create()
    }

    override fun onStart() {
        super.onStart()
        editText = dialog?.findViewById(R.id.command_line_args) ?: error("Cannot find edit text")
        if (!clArgs.isNullOrEmpty()) {
            editText.setText(clArgs)
            editText.setSelection(editText.text.length)
        }
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            val args = editText.text.toString().trim()
            callback.onCommandLineArgsUpdate(args)
        }
    }
}