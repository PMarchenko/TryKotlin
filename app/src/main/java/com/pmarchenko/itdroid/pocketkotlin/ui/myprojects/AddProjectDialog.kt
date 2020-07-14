package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.databinding.DialogAddProjectBinding
import com.pmarchenko.itdroid.pocketkotlin.projects.newProject
import com.pmarchenko.itdroid.pocketkotlin.setTextAndSelection

/**
 * @author Pavel Marchenko
 */
class AddProjectDialog : DialogFragment() {

    private lateinit var binding: DialogAddProjectBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_add_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (dialog as AlertDialog).setView(view)

        binding = DialogAddProjectBinding.bind(view)

        if (savedInstanceState == null) {
            binding.dialogContent.projectNameView.setTextAndSelection(R.string.dialog__add_project__default_name)
        }

        binding.dialogContent.projectNameView.doAfterTextChanged {
            positiveButton?.isEnabled = it?.isNotEmpty() ?: false
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog__add_project__title)
            .setNegativeButton(R.string.dialog__add_project__negative_button, null)
            .setPositiveButton(R.string.dialog__add_project__positive_button) { _, _ ->
                ViewModelProviders.of(requireParentFragment())
                    .get(MyProjectsViewModel::class.java)
                    .addNewProject(newProject(name, withMain))
            }
            .create()

    private val positiveButton: Button?
        get() = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)

    private val name
        get() = binding.dialogContent.projectNameView.text.toString().trim()

    private val withMain
        get() = binding.includeMain.isChecked

    companion object {

        private const val TAG = "AddProjectDialog"

        fun show(fm: FragmentManager) {
            AddProjectDialog().show(fm, TAG)
        }
    }
}

