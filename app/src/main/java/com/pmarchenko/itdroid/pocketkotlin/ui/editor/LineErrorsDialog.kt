package com.pmarchenko.itdroid.pocketkotlin.ui.editor

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.data.model.EditorError
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.ProjectFile

/**
 * @author Pavel Marchenko
 */
class LineErrorsDialog : DialogFragment() {

    companion object {

        private const val TAG = "LineErrorsDialog"

        private const val KEY_FILE_NAME = "$TAG.FILE_NAME"
        private const val KEY_LINE = "$TAG.LINE"
        private const val KEY_ERROR = "$TAG.ERRORS"

        fun show(fragment: Fragment, file: ProjectFile, line: Int, errors: ArrayList<EditorError>) {
            val dialog = LineErrorsDialog()
            dialog.arguments = Bundle(3).apply {
                putString(KEY_FILE_NAME, file.name)
                putInt(KEY_LINE, line)
                putParcelableArrayList(KEY_ERROR, errors)
            }
            dialog.show(fragment.childFragmentManager, TAG)
        }
    }

    private var fileName: String = ""
    private var line: Int = -1
    private var errors: ArrayList<EditorError> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fileName = arguments?.getString(KEY_FILE_NAME) ?: fileName
        line = arguments?.getInt(KEY_LINE) ?: -1
        errors = arguments?.getParcelableArrayList(KEY_ERROR) ?: ArrayList()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog__line_errors__title, fileName, line))
            .setMessage(errorsToMessage(errors))
            .setPositiveButton(R.string.dialog__line_errors__positive_button, null)
            .create()

    private fun errorsToMessage(errors: ArrayList<EditorError>) =
        errors
            .map { error -> "${error.severity}: ${error.message}" }
            .mapTo(LinkedHashSet()) { error -> error }
            .joinToString("\n") { error -> error }

}