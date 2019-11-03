package com.pmarchenko.itdroid.pocketkotlin.ui.editor


import android.os.Bundle
import android.view.*
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.text.parseAsHtml
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.core.extentions.bindView
import com.pmarchenko.itdroid.pocketkotlin.core.extentions.isVisible
import com.pmarchenko.itdroid.pocketkotlin.core.extentions.scale
import com.pmarchenko.itdroid.pocketkotlin.core.extentions.setVisibility
import com.pmarchenko.itdroid.pocketkotlin.core.utils.toast
import com.pmarchenko.itdroid.pocketkotlin.data.model.EditorError
import com.pmarchenko.itdroid.pocketkotlin.data.model.project.ProjectException
import com.pmarchenko.itdroid.pocketkotlin.domain.db.AppDatabase
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.ProjectType
import com.pmarchenko.itdroid.pocketkotlin.domain.executor.ThrottleTaskExecutor
import com.pmarchenko.itdroid.pocketkotlin.domain.network.NetworkKotlinProjectExecutionService
import com.pmarchenko.itdroid.pocketkotlin.domain.repository.ProjectsRepository
import com.pmarchenko.itdroid.pocketkotlin.ui.TabLayoutMediator
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.ProjectAdapter
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.ChangeProjectNameDialog

/**
 * @author Pavel Marchenko
 */
class EditorFragment : Fragment(), CommandLineArgsDialogCallback, EditorCallback {

    private lateinit var viewModel: EditorViewModel

    private val viewModelProvider = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            //todo introduce DI
            if (modelClass.isAssignableFrom(EditorViewModel::class.java)) {
                val projectDao =
                    AppDatabase.getDatabase(requireActivity().applicationContext).getProjectDao()
                val executionService = NetworkKotlinProjectExecutionService
                val projectRepo = ProjectsRepository(projectDao, executionService)
                val taskExecutor = ThrottleTaskExecutor()

                @Suppress("UNCHECKED_CAST")
                return EditorViewModel(projectRepo, taskExecutor) as T
            }
            error("Cannot create viewModel for $modelClass")
        }
    }

    private val executeCodeFab by bindView<FloatingActionButton>(R.id.fabEditor)
    private val executeProgressView by bindView<View>(R.id.executeProgress)
    private val mainProgressView by bindView<View>(R.id.progressMain)
    private val tabs by bindView<TabLayout>(R.id.editorTabs)
    private val viewPager by bindView<ViewPager2>(R.id.pages)

    private lateinit var adapter: ProjectAdapter

    private val backButtonResolver = BackButtonResolver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (backButtonResolver.allowBackPressed()) {
                isEnabled = false
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            //else -> todo bug show info message
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelProvider).get(EditorViewModel::class.java)
        return inflater.inflate(R.layout.fragment_editor, container, false)
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        super.onViewCreated(root, savedInstanceState)
        initUI()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("tabPosition", viewPager.currentItem)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val projectId = arguments?.getLong("projectId") ?: -1L

        if (projectId > 0) {
            viewModel.loadProject(projectId)
        } else {
            toast(R.string.error_message__invalid_project_id)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val project = viewModel.getProject()

        menu.add(0, R.id.projectArgs, 0, R.string.menu_item__command_line_args)
            .setIcon(R.drawable.ic_command_line_args_empty_24dp)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
        menu.add(0, R.id.clearLogs, 0, R.string.menu_item__clear_logs)

        if (project != null) {
            when (project.projectType) {
                ProjectType.EXAMPLE -> menu.add(
                    0,
                    R.id.resetExample,
                    0,
                    R.string.menu_item__reset_example
                )
                ProjectType.USER_PROJECT -> if (project.dateModified <= 0) menu.add(
                    0,
                    R.id.changeName,
                    0,
                    R.string.menu_item__change_name
                )
            }

            menu.findItem(R.id.projectArgs)?.setIcon(
                if (viewModel.getProjectArgs().isEmpty()) {
                    R.drawable.ic_command_line_args_empty_24dp
                } else {
                    R.drawable.ic_command_line_args_24dp
                }
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.projectArgs -> {
            if (viewModel.hasProject()) {
                CommandLineArgsDialog.show(this, viewModel.getProjectArgs())
            }
            true
        }
        R.id.clearLogs -> {
            viewModel.clearLogs()
            true
        }
        R.id.changeName -> {
            viewModel.getProject()?.let { ChangeProjectNameDialog.show(this, it) }
            true
        }
        R.id.resetExample -> {
            adapter.resetProject()
            viewModel.getProject()?.let { viewModel.resetExample(it) }
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initUI() {
        view?.findViewById<Toolbar>(R.id.toolbar)?.let {
            (requireActivity() as AppCompatActivity).setSupportActionBar(it)
        }
        adapter = ProjectAdapter(requireContext(), this)
        viewPager.isUserInputEnabled = false
        viewPager.adapter = adapter

        TabLayoutMediator(tabs, viewPager, true,
            TabLayoutMediator.OnConfigureTabCallback { tab, position ->
                tab.text = adapter.getTitle(position)
            }
        ).attach()

        executeCodeFab.setOnClickListener { viewModel.executeProject() }

        viewModel.viewState.observe(viewLifecycleOwner, Observer { onNewViewState(it) })
        viewModel.log.observe(viewLifecycleOwner, Observer { adapter.setLog(it) })
    }

    private fun onNewViewState(state: EditorViewState) {
        val project = state.project
        if (project == null) {
            mainProgressView.setVisibility(true)
            tabs.setVisibility(false)
            executeProgressView.setVisibility(false)
            executeCodeFab.setVisibility(false)
        } else {
            (requireActivity() as AppCompatActivity).supportActionBar?.let {
                it.title = project.name
            }
            mainProgressView.setVisibility(false)
            tabs.setVisibility(true)
            if (executeProgressView.isVisible() != state.progressVisibility) {
                executeProgressView.setVisibility(state.progressVisibility)
                executeProgressView.animate().scale(if (state.progressVisibility) 1f else 0f)
                    .setDuration(150).start()
            }

            if (executeCodeFab.isEnabled != !state.progressVisibility) {
                executeCodeFab.isEnabled = !state.progressVisibility
                executeCodeFab.animate().scale(if (state.progressVisibility) 0f else 1f)
                    .setDuration(150).start()
            }
            val currentItem =
                if (viewPager.adapter?.itemCount ?: -1 == 0) 1 else viewPager.currentItem
            adapter.setProject(project, state.executionResult?.errors)
            viewPager.currentItem = currentItem
        }
        requireActivity().invalidateOptionsMenu()

        state.consume()
    }

    override fun onCommandLineArgsUpdate(args: String) {
        viewModel.setCommandLineArgs(args)
    }

    override fun showErrorDetails(file: ProjectFile, line: Int, errors: ArrayList<EditorError>) {
        LineErrorsDialog.show(this, file, line, errors)
    }

    override fun showExceptionDetails(exception: ProjectException) {
        TextDialog.show(
            this,
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
            viewPager.setCurrentItem(position, false)
            if (line >= 0) adapter.applySelection(fileName, line, linePosition)
        }
    }

    override fun onProjectName(project: Project, name: String) {
        viewModel.updateProjectName(project, name)
    }

    companion object {

        const val TAG = "EditorFragment"

        fun newInstance(projectId: Long): Fragment =
            EditorFragment().apply {
                arguments = Bundle(1).apply {
                    putLong("projectId", projectId)
                }
            }
    }
}
