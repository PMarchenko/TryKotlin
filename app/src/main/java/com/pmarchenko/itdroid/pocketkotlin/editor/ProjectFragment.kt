package com.pmarchenko.itdroid.pocketkotlin.editor


import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.pmarchenko.itdroid.pocketkotlin.MainActivity
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.editor.adapter.ProjectAdapter
import com.pmarchenko.itdroid.pocketkotlin.extentions.findView
import com.pmarchenko.itdroid.pocketkotlin.extentions.scale
import com.pmarchenko.itdroid.pocketkotlin.extentions.setVisibility
import com.pmarchenko.itdroid.pocketkotlin.model.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.utils.TabLayoutMediator

/**
 * @author Pavel Marchenko
 */
class ProjectFragment : Fragment(), CommandLineArgsDialogCallback, ProjectFileEditCallback {

    private lateinit var viewModel: ProjectViewModel

    private val executeCodeFab by findView<FloatingActionButton>(R.id.editor_fab)
    private val progressView by findView<View>(R.id.progress)
    private val tabs by findView<TabLayout>(R.id.tabs)
    private val viewPager by findView<ViewPager2>(R.id.pages)

    private lateinit var adapter: ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_project, container, false)
    }

    override fun onViewCreated(root: View, savedInstanceState: Bundle?) {
        super.onViewCreated(root, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ProjectViewModel::class.java)
        initUI()
        if (savedInstanceState == null) viewPager.post { viewPager.currentItem = 1 }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.editor_menu, menu)
        menu.findItem(R.id.project_args)?.setIcon(
                if (viewModel.project.args.isEmpty())
                    R.drawable.ic_command_line_args_empty_24dp
                else
                    R.drawable.ic_command_line_args_24dp
        )
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.project_args -> {
            editCommandLineArgs()
            true
        }
        R.id.clear_logs -> {
            clearLogs()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initUI() {
        (activity as MainActivity).setSupportActionBar(view?.findViewById<Toolbar>(R.id.toolbar))

        adapter = ProjectAdapter(requireContext(), this)
        viewPager.adapter = adapter
        adapter.setProject(viewModel.project)

        TabLayoutMediator(tabs, viewPager, true,
                TabLayoutMediator.OnConfigureTabCallback { tab, position ->
                    tab.text = adapter.getTitle(position)
                }
        ).attach()

        executeCodeFab.setOnClickListener { viewModel.executeProject() }

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state -> onNewViewState(state) })
        viewModel.log.observe(viewLifecycleOwner, Observer { logs ->
            adapter.setLog(logs)
        })
    }

    private fun onNewViewState(state: ProjectViewState) {
        progressView.setVisibility(state.progressVisibility)
        progressView.animate().scale(if (state.progressVisibility) 1f else 0f).setDuration(150).start()

        executeCodeFab.isEnabled = !state.progressVisibility
        executeCodeFab.animate().scale(if (state.progressVisibility) 0f else 1f).setDuration(150).start()

        adapter.projectExecutionResult = state.executionResult
        state.consume()
    }

    private fun editCommandLineArgs() {
        CommandLineArgsDialog.show(this, viewModel.project.args)
    }

    private fun clearLogs() {
        viewModel.clearLogs()
    }

    override fun onCommandLineArgsUpdate(args: String) {
        viewModel.project.args = args
        requireActivity().invalidateOptionsMenu()
    }

    override fun onProjectFileEdit(file: ProjectFile) {
        adapter.clearErrors(file)
    }
}
