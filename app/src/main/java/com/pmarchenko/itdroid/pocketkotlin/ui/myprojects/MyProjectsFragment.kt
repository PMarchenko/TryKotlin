package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.databinding.FragmentMyProjectsBinding
import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project
import com.pmarchenko.itdroid.pocketkotlin.ui.EventObserver
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorActivity
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.adapter.MyProjectsAdapter
import com.pmarchenko.itdroid.pocketkotlin.utils.dp

class MyProjectsFragment : Fragment() {

    private lateinit var binding: FragmentMyProjectsBinding

    private lateinit var viewModel: MyProjectsViewModel

    private lateinit var adapter: MyProjectsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(MyProjectsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_my_projects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyProjectsBinding.bind(view)
        initUi()
        viewModel.viewState.observe(viewLifecycleOwner, Observer { updateUI(it) })
        viewModel.openProject.observe(viewLifecycleOwner, EventObserver { openProject(it) })
    }

    private fun initUi() {
        binding.fabAddProject.setOnClickListener { addNewProject() }

        binding.projectsList.layoutManager =
            GridLayoutManager(
                requireContext(),
                resources.getInteger(R.integer.projectsListGridSize)
            )
        binding.projectsList.adapter = MyProjectsAdapter(projectsCallback).also { adapter = it }
        binding.projectsList.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(8.dp, 8.dp, 8.dp, 8.dp)
            }
        })
    }

    private fun updateUI(state: MyProjectsViewState) {
        binding.progress.isVisible = state.isLoading
        binding.emptyView.isVisible = state.projects.isEmpty()
        binding.projectsList.isVisible = !binding.emptyView.isVisible

        val atTop = !binding.projectsList.canScrollVertically(-1)
        adapter.setProjects(state.projects)
        if (atTop && adapter.itemCount > 0) {
            binding.projectsList.layoutManager?.scrollToPosition(0)
        }
    }

    private fun addNewProject() {
        AddProjectDialog.show(childFragmentManager)
    }

    private fun openProject(projectId: Long) {
        startActivity(
            EditorActivity.asDeepLinkIntent(projectId).setPackage(requireContext().packageName)
        )
    }

    private val projectsCallback: ProjectCallback = object : ProjectCallback {

        override fun onProjectClick(project: Project) {
            openProject(project.id)
        }

        override fun onProjectMenuClick(anchor: View, project: Project) {
            PopupMenu(requireContext(), anchor)
                .apply {
                    inflate(R.menu.project)
                    setOnMenuItemClickListener { item ->
                        when (item?.itemId) {
                            R.id.action_change_name -> {
                                ChangeProjectNameDialog.show(childFragmentManager, project)
                                true
                            }
                            R.id.action_delete -> {
                                viewModel.deleteProject(project)
                                true
                            }
                            else -> false
                        }
                    }
                }
                .show()
        }
    }
}