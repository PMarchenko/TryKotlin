package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.utils.TextWatcherAdapter

/**
 * @author Pavel Marchenko
 */
class AddProjectDialog : DialogFragment(), DialogInterface.OnClickListener {

    companion object {

        private const val TAG = "AddProjectDialog"

        fun show(fragment: Fragment) {
            val dialog = AddProjectDialog()
            dialog.setTargetFragment(fragment, 0)
            dialog.show(fragment.requireFragmentManager(), TAG)
        }
    }

    private lateinit var nameView: EditText
    private lateinit var isIncludeMain: CheckBox
    private lateinit var callback: ProjectCallback

    private var hasName = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = targetFragment as ProjectCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let { hasName = it.getBoolean("has_name") }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog__add_project__title)
            .setView(R.layout.dialog_add_project)
            .setNegativeButton(R.string.dialog__add_project__negative_button, null)
            .setPositiveButton(R.string.dialog__add_project__positive_button, this)
            .create()
    }

    override fun onStart() {
        super.onStart()
        nameView = dialog?.findViewById(R.id.projectName) ?: error("Cannot find name view")
        nameView.addTextChangedListener(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable?) {
                positiveButton().isEnabled = s?.isNotEmpty() ?: false
            }
        })
        isIncludeMain = dialog?.findViewById(R.id.includeMain) ?: error("Cannot find is main view")
        positiveButton().isEnabled = hasName
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("has_name", getProjectName().isNotEmpty())
    }

    private fun positiveButton() = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)

    override fun onClick(dialog: DialogInterface?, which: Int) {
        val projectName = getProjectName()
        if (which == DialogInterface.BUTTON_POSITIVE && projectName.isNotEmpty()) {
            val includeMain = isIncludeMain.isChecked
            callback.onAddProject(if (includeMain) Project.withMainFun(projectName) else Project.empty(projectName))
        }
    }

    private fun getProjectName() = nameView.text.toString().trim()
}