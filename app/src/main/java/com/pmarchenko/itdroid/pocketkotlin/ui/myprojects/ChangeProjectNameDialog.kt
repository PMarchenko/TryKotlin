package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.model.project.Project
import com.pmarchenko.itdroid.pocketkotlin.utils.TextWatcherAdapter

/**
 * @author Pavel Marchenko
 */
class ChangeProjectNameDialog : DialogFragment(), DialogInterface.OnClickListener {

    companion object {

        private const val TAG = "AddProjectDialog"

        fun show(fragment: Fragment, project: Project) {
            val dialog = ChangeProjectNameDialog()
            dialog.arguments = Bundle(1).apply {
                putParcelable("project", project)
            }
            dialog.setTargetFragment(fragment, 0)
            dialog.show(fragment.requireFragmentManager(), TAG)
        }
    }

    private lateinit var nameView: EditText
    private lateinit var callback: ProjectNameCallback

    private lateinit var project: Project
    private lateinit var projectName: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = targetFragment as ProjectNameCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        project = arguments?.getParcelable<Project>("project") ?: error("No project provided")
        projectName = savedInstanceState?.getString("name") ?: project.name
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(R.layout.dialog_change_project_name)
            .setNegativeButton(R.string.dialog__add_project__negative_button, null)
            .setPositiveButton(R.string.dialog__change_project_name__positive_button, this)
            .create()
    }

    override fun onStart() {
        super.onStart()
        nameView = dialog?.findViewById(R.id.projectName) ?: error("Cannot find name view")
        if (getProjectName().isEmpty()) {
            nameView.setText(projectName)
            nameView.setSelection(projectName.length)
        }
        nameView.addTextChangedListener(object : TextWatcherAdapter() {
            override fun afterTextChanged(s: Editable?) {
                updatePositiveButtonState(s?.toString() ?: "")
            }
        })
        updatePositiveButtonState(getProjectName())
    }

    private fun updatePositiveButtonState(name: String) {
        val button = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
        button.isEnabled = name.isNotEmpty() && name != project.name
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("name", getProjectName())
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        val projectName = getProjectName()
        if (which == DialogInterface.BUTTON_POSITIVE && projectName.isNotEmpty()) {
            callback.onProjectName(project, getProjectName())
        }
    }

    private fun getProjectName() = nameView.text.toString().trim()
}