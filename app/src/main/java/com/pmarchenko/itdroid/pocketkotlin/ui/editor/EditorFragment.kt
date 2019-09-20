package com.pmarchenko.itdroid.pocketkotlin.ui.editor


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.extentions.findView
import com.pmarchenko.itdroid.pocketkotlin.extentions.isVisible
import com.pmarchenko.itdroid.pocketkotlin.extentions.scale
import com.pmarchenko.itdroid.pocketkotlin.extentions.setVisibility
import com.pmarchenko.itdroid.pocketkotlin.model.EditorError
import com.pmarchenko.itdroid.pocketkotlin.model.project.Project
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.ProjectAdapter
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.ChangeProjectNameDialog
import com.pmarchenko.itdroid.pocketkotlin.utils.TabLayoutMediator
import com.pmarchenko.itdroid.pocketkotlin.utils.toast

/**
 * @author Pavel Marchenko
 */
class EditorFragment : Fragment(), CommandLineArgsDialogCallback, EditorCallback {

    private lateinit var viewModel: EditorViewModel

    private val executeCodeFab by findView<FloatingActionButton>(R.id.fabEditor)
    private val executeProgressView by findView<View>(R.id.executeProgress)
    private val mainProgressView by findView<View>(R.id.progressMain)
    private val tabs by findView<TabLayout>(R.id.editorTabs)
    private val viewPager by findView<ViewPager2>(R.id.pages)

    private lateinit var adapter: ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this).get(EditorViewModel::class.java)
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
        if (viewModel.hasProject()) {
            inflater.inflate(R.menu.editor_menu, menu)
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
        R.id.actionChangeName -> {
            viewModel.getProject()?.let { project ->
                ChangeProjectNameDialog.show(this, project)
            }
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
            TabLayoutMediator.OnConfigureTabCallback { tab, position -> tab.text = adapter.getTitle(position) }
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
            (requireActivity() as AppCompatActivity).supportActionBar?.let { it.title = project.name }
            mainProgressView.setVisibility(false)
            tabs.setVisibility(true)
            if (executeProgressView.isVisible() != state.progressVisibility) {
                executeProgressView.setVisibility(state.progressVisibility)
                executeProgressView.animate().scale(if (state.progressVisibility) 1f else 0f).setDuration(150).start()
            }

            if (executeCodeFab.isEnabled != !state.progressVisibility) {
                executeCodeFab.isEnabled = !state.progressVisibility
                executeCodeFab.animate().scale(if (state.progressVisibility) 0f else 1f).setDuration(150).start()
            }
            val currentItem = if (viewPager.adapter!!.itemCount == 0) 1 else viewPager.currentItem
            adapter.setProject(project, state.executionResult?.errors)
            // TODO BUG view pager resets its position to 0
            viewPager.post {
                viewPager.currentItem = currentItem
            }
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

        fun newInstance(projectId: Long): Fragment {
            val args = Bundle(1)
            args.putLong("projectId", projectId)

            val fragment = EditorFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
