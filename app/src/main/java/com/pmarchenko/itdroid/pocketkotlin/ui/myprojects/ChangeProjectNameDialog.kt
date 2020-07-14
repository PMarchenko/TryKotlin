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
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.databinding.ContentProjectNameBinding
import com.pmarchenko.itdroid.pocketkotlin.databinding.DialogChangeProjectNameBinding
import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project
import com.pmarchenko.itdroid.pocketkotlin.setTextAndSelection
import com.pmarchenko.itdroid.pocketkotlin.ui.fromArgs
import com.pmarchenko.itdroid.pocketkotlin.ui.withArgs
import com.pmarchenko.itdroid.pocketkotlin.utils.usecase.executeUseCase

/**
 * @author Pavel Marchenko
 */
class ChangeProjectNameDialog : DialogFragment() {

    private lateinit var binding: ContentProjectNameBinding
    private val project by lazy { fromArgs<Project>(ARG_PROJECT) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_change_project_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireDialog() as AlertDialog).setView(view)
        binding = DialogChangeProjectNameBinding.bind(view).dialogContent
        if (savedInstanceState == null) {
            binding.projectNameView.setTextAndSelection(project.name)
        }
        binding.projectNameView.doAfterTextChanged {
            positiveButton?.isEnabled = it?.isNotEmpty() ?: false
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setNegativeButton(R.string.dialog__add_project__negative_button, null)
            .setPositiveButton(R.string.dialog__change_project_name__positive_button) { _, _ ->
                with(requireActivity()) {
                    executeUseCase {
                        ChangeProjectUseCase(this, project.copy(name = name))
                    }
                }
            }
            .create()

    private val positiveButton: Button?
        get() = (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)

    private val name
        get() = binding.projectNameView.text.toString().trim()

    companion object {

        private const val TAG = "ChangeProjectNameDialog"
        private const val ARG_PROJECT = "$TAG.PROJECT"

        fun show(fm: FragmentManager, project: Project) {
            ChangeProjectNameDialog()
                .withArgs {
                    putParcelable(ARG_PROJECT, project)
                }
                .show(fm, TAG)
        }
    }
}

