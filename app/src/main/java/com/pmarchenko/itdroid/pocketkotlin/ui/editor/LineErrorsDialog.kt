package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.model.EditorError
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectFile

/**
 * @author Pavel Marchenko
 */
class LineErrorsDialog : DialogFragment() {

    companion object {

        private const val TAG = "LineErrorsDialog"

        fun show(fragment: Fragment, file: ProjectFile, line: Int, errors: ArrayList<EditorError>) {
            val dialog = LineErrorsDialog()
            dialog.arguments = Bundle(3).apply {
                putString("file_name", file.name)
                putInt("line", line)
                putParcelableArrayList("errors", errors)
            }
            dialog.setTargetFragment(fragment, 0)
            dialog.show(fragment.requireFragmentManager(), TAG)
        }
    }

    private var fileName: String = ""
    private var line: Int = -1
    private var errors: ArrayList<EditorError> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fileName = arguments?.getString("file_name") ?: fileName
        line = arguments?.getInt("line") ?: -1
        errors = arguments?.getParcelableArrayList("errors") ?: ArrayList()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.dialog__line_errors__title, fileName, line))
                .setMessage(errorsToMessage(errors))
                .setPositiveButton(R.string.dialog__line_errors__positive_button, null)
                .create()
    }

    private fun errorsToMessage(errors: ArrayList<EditorError>): CharSequence {
        val out = StringBuilder()
        val duplicates = mutableListOf<String>()
        errors.forEach { error ->
            val message = "${error.severity}: ${error.message}"
            if (!duplicates.contains(message)) {
                duplicates.add(message)
                if (out.isNotEmpty()) out.append('\n')
                out.append(message)
            }
        }
        return out.toString()
    }
}