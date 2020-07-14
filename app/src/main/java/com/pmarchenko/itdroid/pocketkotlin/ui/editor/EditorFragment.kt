package com.pmarchenko.itdroid.pocketkotlin.ui.editor


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.checkAllMatched
import com.pmarchenko.itdroid.pocketkotlin.databinding.FragmentEditorBinding
import com.pmarchenko.itdroid.pocketkotlin.databinding.FragmentEditorContentBinding
import com.pmarchenko.itdroid.pocketkotlin.databinding.ViewToolsPanelContentBinding
import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectException
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.projects.model.ProjectType
import com.pmarchenko.itdroid.pocketkotlin.scale
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.ProjectAdapter
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.model.EditorError
import com.pmarchenko.itdroid.pocketkotlin.ui.fromArgs
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.ChangeProjectNameDialog
import com.pmarchenko.itdroid.pocketkotlin.ui.toast
import com.pmarchenko.itdroid.pocketkotlin.ui.withArgs

/**
 * @author Pavel Marchenko
 */
class EditorFragment : Fragment() {

    private var _binding: FragmentEditorBinding? = null
    private val binding: FragmentEditorBinding
        get() = _binding!!

    private var _contentBinding: FragmentEditorContentBinding? = null
    private val contentBinding: FragmentEditorContentBinding
        get() = _contentBinding!!

    private var _editorToolsBinding: ViewToolsPanelContentBinding? = null
    private val editorToolsBinding: ViewToolsPanelContentBinding
        get() = _editorToolsBinding!!

    private lateinit var viewModel: EditorViewModel

    private lateinit var adapter: ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(EditorViewModel::class.java)

        _binding = FragmentEditorBinding.inflate(inflater, container, false)
        _contentBinding = FragmentEditorContentBinding.bind(binding.root)
        _editorToolsBinding = ViewToolsPanelContentBinding.bind(contentBinding.toolsPanel)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()

        viewModel.viewState.observe(viewLifecycleOwner, Observer { updateUi(it) })
        viewModel.loadProject(fromArgs(ARG_PROJECT_ID))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.editor, menu)

        menu.findItem(R.id.projectArgs)?.setIcon(
            if (viewModel.getProjectArgs().isEmpty()) {
                R.drawable.ic_command_line_args_empty_24dp
            } else {
                R.drawable.ic_command_line_args_24dp
            }
        )
        viewModel.getProject()?.projectType?.let {
            when (it) {
                ProjectType.EXAMPLE -> menu.findItem(R.id.changeName)?.isVisible = false
                ProjectType.USER_PROJECT -> {
                }
            }.checkAllMatched
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.projectArgs -> {
            if (viewModel.hasProject) {
                CommandLineArgsDialog.show(childFragmentManager, viewModel.getProjectArgs())
            }
            true
        }
        R.id.clearLogs -> {
            viewModel.clearLogs()
            true
        }
        R.id.changeName -> {
            viewModel.getProject()?.let { ChangeProjectNameDialog.show(childFragmentManager, it) }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initUi() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        binding.fabEditor.isEnabled = false
        binding.fabEditor.setOnClickListener { viewModel.executeProject() }

        contentBinding.pages.isUserInputEnabled = false
        adapter = ProjectAdapter(requireContext(), editorCallback)
        contentBinding.pages.adapter = adapter

        editorToolsBinding.logs.setOnClickListener(toolsCallback)
        editorToolsBinding.lt.setOnClickListener(toolsCallback)
        editorToolsBinding.gt.setOnClickListener(toolsCallback)

        TabLayoutMediator(binding.editorTabs, contentBinding.pages, true) { tab, position ->
            tab.text = adapter.getTitle(position)
        }.attach()
    }

    private fun updateUi(state: EditorViewState) {
        //todo rethink EditorViewState
        val project = state.project
        if (project == null) {
            binding.progressMain.isVisible = state.progressVisibility
            contentBinding.pages.isVisible = false
            binding.executeProgress.isVisible = false
            binding.fabEditor.isVisible = false
        } else {
            (requireActivity() as AppCompatActivity).supportActionBar?.let {
                it.title = project.name
            }
            binding.progressMain.isVisible = false
            contentBinding.pages.isVisible = true
            binding.fabEditor.isVisible = true
            if (binding.executeProgress.isVisible != state.progressVisibility) {
                binding.executeProgress.isVisible = state.progressVisibility
                binding.executeProgress.animate().scale(if (state.progressVisibility) 1f else 0f)
                    .setDuration(150).start()
            }

            if (binding.fabEditor.isEnabled != !state.progressVisibility) {
                binding.fabEditor.isEnabled = !state.progressVisibility
                binding.fabEditor.animate().scale(if (state.progressVisibility) 0f else 1f)
                    .setDuration(150).start()
            }
            adapter.setProject(project, state.executionResult?.errors)
        }
        requireActivity().invalidateOptionsMenu()
    }

    private val editorCallback = object : EditorCallback {

        override fun showErrorDetails(
            file: ProjectFile,
            line: Int,
            errors: ArrayList<EditorError>
        ) {
            LineErrorsDialog.show(childFragmentManager, file, line, errors)
        }

        override fun showExceptionDetails(exception: ProjectException) {
            TextDialog.show(
                childFragmentManager,
                exception.fullName,
                exception.message.parseAsHtml()
            )
        }

        override fun onFileEdited(project: Project, file: ProjectFile, program: String) {
            viewModel.editProjectFile(project, file, program)
        }

        override fun openFile(fileName: String, line: Int, linePosition: Int) {
            val position = adapter.getFilePosition(fileName)
            if (position != RecyclerView.NO_POSITION) {
                contentBinding.pages.setCurrentItem(position, false)
                if (line >= 0) adapter.applySelection(fileName, line, linePosition)
            }
        }

        override fun onProjectName(project: Project, name: String) {
            viewModel.updateProjectName(project, name)
        }
    }

    private val toolsCallback = View.OnClickListener {
        when (it) {
            editorToolsBinding.logs -> toast("logs")
            editorToolsBinding.lt -> toast("lt")
            editorToolsBinding.gt -> toast("gt")
        }
    }

    companion object {

        const val TAG = "EditorFragment"
        private const val ARG_PROJECT_ID = "$TAG.PROJECT_ID"

        fun newInstance(projectId: Long): Fragment =
            EditorFragment()
                .withArgs {
                    putLong(ARG_PROJECT_ID, projectId)
                }
    }
}
