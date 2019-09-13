package com.pmarchenko.itdroid.pocketkotlin.ui.editor


import android.content.Context
import android.os.Bundle
import android.view.*
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
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.adapter.ProjectAdapter
import com.pmarchenko.itdroid.pocketkotlin.utils.TabLayoutMediator

/**
 * @author Pavel Marchenko
 */
class EditorFragment : Fragment(), CommandLineArgsDialogCallback, EditorCallback, EditorBridge {

    companion object {
        const val TAG = "EditorFragment"
    }

    private lateinit var editorHost: EditorHost
    private lateinit var viewModel: EditorViewModel

    private val executeCodeFab by findView<FloatingActionButton>(R.id.editor_fab)
    private val progressView by findView<View>(R.id.progress)
    private val tabs by findView<TabLayout>(R.id.editor_tabs)
    private val viewPager by findView<ViewPager2>(R.id.pages)

    private lateinit var adapter: ProjectAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        editorHost = context as EditorHost
        editorHost.registerEditor(this)
    }

    override fun onDetach() {
        super.onDetach()
        editorHost.unregisterEditor()
    }

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

        if (savedInstanceState == null) viewPager.post { viewPager.currentItem = 1 }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        viewModel.project?.let { project ->
            inflater.inflate(R.menu.editor_menu, menu)
            menu.findItem(R.id.projectArgs)?.setIcon(
                if (project.args.isEmpty()) {
                    R.drawable.ic_command_line_args_empty_24dp
                } else {
                    R.drawable.ic_command_line_args_24dp
                }
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.projectArgs -> {
            viewModel.project?.let { project -> CommandLineArgsDialog.show(this, project.args) }
            true
        }
        R.id.clearLogs -> {
            viewModel.clearLogs()
            true
        }
        R.id.closeProject -> {
            viewModel.project = null
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun initUI() {
        adapter = ProjectAdapter(requireContext(), this)
        viewPager.isUserInputEnabled = false
        viewPager.adapter = adapter
        adapter.updateState(viewModel.project, null)

        TabLayoutMediator(tabs, viewPager, true,
            TabLayoutMediator.OnConfigureTabCallback { tab, position -> tab.text = adapter.getTitle(position) }
        ).attach()

        executeCodeFab.setOnClickListener { viewModel.executeProject() }

        viewModel.viewState.observe(viewLifecycleOwner, Observer { onNewViewState(it) })
        viewModel.log.observe(viewLifecycleOwner, Observer { adapter.setLog(it) })
    }

    private fun onNewViewState(state: EditorViewState) {
        val project = state.project
        val hasProject = project != null
        if (hasProject) {
            executeCodeFab.setVisibility(true)
            tabs.setVisibility(true)

            if (progressView.isVisible() != state.progressVisibility) {
                progressView.setVisibility(state.progressVisibility)
                progressView.animate().scale(if (state.progressVisibility) 1f else 0f).setDuration(150).start()
            }

            executeCodeFab.setVisibility(true)
            if (executeCodeFab.isEnabled != !state.progressVisibility) {
                executeCodeFab.isEnabled = !state.progressVisibility
                executeCodeFab.animate().scale(if (state.progressVisibility) 0f else 1f).setDuration(150).start()
            }
        } else {
            progressView.setVisibility(false)
            executeCodeFab.setVisibility(false)
            tabs.setVisibility(false)
        }

        adapter.updateState(project, state.executionResult)

        requireActivity().invalidateOptionsMenu()

        state.consume()
    }

    override fun onCommandLineArgsUpdate(args: String) {
        viewModel.setCommandLineArgs(args)
    }

    override fun showErrorDetails(file: ProjectFile, line: Int, errors: ArrayList<EditorError>) {
        LineErrorsDialog.show(this, file, line, errors)
    }

    override fun onFileEdited(file: ProjectFile, text: String) {
        viewModel.editProjectFile(file, text)
    }

    override fun openFile(fileName: String, line: Int, linePosition: Int) {
        val position = adapter.getFilePosition(fileName)
        if (position != RecyclerView.NO_POSITION) {
            viewPager.setCurrentItem(position, false)
            if (line >= 0) adapter.applySelection(fileName, line, linePosition)
        }
    }
}
