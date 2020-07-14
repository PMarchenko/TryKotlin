package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.databinding.DialogCommandLineArgsBinding
import com.pmarchenko.itdroid.pocketkotlin.setTextAndSelection
import com.pmarchenko.itdroid.pocketkotlin.ui.fromArgs
import com.pmarchenko.itdroid.pocketkotlin.ui.withArgs

/**
 * @author Pavel Marchenko
 */
class CommandLineArgsDialog : DialogFragment() {

    private lateinit var binding: DialogCommandLineArgsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_command_line_args, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireDialog() as AlertDialog).setView(view)
        binding = DialogCommandLineArgsBinding.bind(view)
        if (savedInstanceState == null) {
            binding.commandLineArgs.setTextAndSelection(fromArgs<String>(ARG_ARGS))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) =
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog__command_line_args__title)
            .setNegativeButton(R.string.dialog__command_line_args__negative_button, null)
            .setPositiveButton(R.string.dialog__command_line_args__positive_button) { _, _ ->
                ViewModelProviders.of(requireParentFragment())
                    .get(EditorViewModel::class.java)
                    .setCommandLineArgs(binding.commandLineArgs.text.toString().trim())
            }
            .create()


    companion object {

        private const val TAG = "CommandLineArgsDialog"
        private const val ARG_ARGS = "$TAG.ARGS"

        fun show(fm: FragmentManager, args: String) {
            CommandLineArgsDialog()
                .withArgs {
                    putString(ARG_ARGS, args)
                }.show(fm, TAG)
        }
    }
}