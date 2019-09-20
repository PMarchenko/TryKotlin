package com.pmarchenko.itdroid.pocketkotlin.ui.editor

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

    private lateinit var argsView: EditText
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
                .setTitle(R.string.dialog__command_line_args__title)
                .setView(R.layout.dialog_command_line_args)
                .setNegativeButton(R.string.dialog__command_line_args__negative_button, null)
                .setPositiveButton(R.string.dialog__command_line_args__positive_button, this)
                .create()
    }

    override fun onStart() {
        super.onStart()
        argsView = dialog?.findViewById(R.id.commandLineArgs) ?: error("Cannot find edit text")
        if (!clArgs.isNullOrEmpty()) {
            argsView.setText(clArgs)
            argsView.setSelection(argsView.text.length)
        }
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            val args = argsView.text.toString().trim()
            callback.onCommandLineArgsUpdate(args)
        }
    }
}