package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.model.EditorError
import com.pmarchenko.itdroid.pocketkotlin.ui.fromArgs
import com.pmarchenko.itdroid.pocketkotlin.ui.withArgs

/**
 * @author Pavel Marchenko
 */
class LineErrorsDialog : DialogFragment() {

    private lateinit var fileName: String
    private var line: Int = 0
    private lateinit var errors: ArrayList<EditorError>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fileName = fromArgs(ARG_FILE_NAME)
        line = fromArgs(ARG_LINE)
        errors = fromArgs(ARG_ERRORS)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog__line_errors__title, fileName, line))
            .setMessage(errorsToMessage(errors))
            .setPositiveButton(R.string.dialog__line_errors__positive_button, null)
            .create()

    private fun errorsToMessage(errors: ArrayList<EditorError>) =
        errors
            .map { "${it.severity}: ${it.message}" }
            .toCollection(LinkedHashSet())
            .joinToString("\n")


    companion object {

        private const val TAG = "LineErrorsDialog"
        private const val ARG_FILE_NAME = "$TAG.FILE_NAME"
        private const val ARG_LINE = "$TAG.LINE"
        private const val ARG_ERRORS = "$TAG.ERRORS"

        fun show(
            fm: FragmentManager,
            file: ProjectFile,
            line: Int,
            errors: ArrayList<EditorError>
        ) {
            LineErrorsDialog().withArgs {
                putString(ARG_FILE_NAME, file.name)
                putInt(ARG_LINE, line)
                putParcelableArrayList(ARG_ERRORS, errors)
            }.show(fm, TAG)
        }
    }
}
