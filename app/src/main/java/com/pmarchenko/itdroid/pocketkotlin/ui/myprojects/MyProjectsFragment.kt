package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.domain.db.AppDatabase
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.domain.extentions.bindView
import com.pmarchenko.itdroid.pocketkotlin.domain.extentions.dp
import com.pmarchenko.itdroid.pocketkotlin.domain.extentions.findView
import com.pmarchenko.itdroid.pocketkotlin.domain.extentions.setVisibility
import com.pmarchenko.itdroid.pocketkotlin.domain.network.DummyProjectExecutionService
import com.pmarchenko.itdroid.pocketkotlin.domain.repository.ProjectsRepository
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorActivity
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.adapter.MyProjectsAdapter


class MyProjectsFragment : Fragment(), ProjectCallback {

    private lateinit var viewModel: MyProjectsViewModel
    private val viewModelProvider = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MyProjectsViewModel::class.java)) {
                val projectDao =
                    AppDatabase.getDatabase(requireActivity().applicationContext).getProjectDao()
                val executionService = DummyProjectExecutionService
                val projectRepo = ProjectsRepository(projectDao, executionService)
                @Suppress("UNCHECKED_CAST")
                return MyProjectsViewModel(projectRepo) as T
            }
            error("Cannot create viewModel for $modelClass")
        }
    }

    private val progressView by bindView<View>(R.id.progress)
    private val emptyView by bindView<View>(R.id.emptyView)
    private val projectsList by bindView<RecyclerView>(R.id.projectsList)
    private lateinit var adapter: MyProjectsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelProvider)
            .get(MyProjectsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_my_projects, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()

        viewModel.userProjects.observe(
            viewLifecycleOwner,
            Observer { onProjects(it ?: emptyList()) })
        viewModel.newProjectCreated.observe(
            viewLifecycleOwner,
            Observer { liveDataHolder -> liveDataHolder.get()?.let { onNewProject(it) } })
    }

    private fun initUI() {
        findView<View>(R.id.fabAddProject).setOnClickListener { addNewProject() }

        projectsList.layoutManager = GridLayoutManager(
            context,
            requireContext().resources.getInteger(R.integer.projectsListGridSize)
        )
        adapter = MyProjectsAdapter(this as ProjectCallback)
        projectsList.adapter = adapter
        projectsList.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(8.dp, 8.dp, 8.dp, 8.dp)
            }
        })
        progressView.setVisibility(true)
    }

    private fun onProjects(projects: List<Project>) {
        progressView.setVisibility(false)
        val atTop = !projectsList.canScrollVertically(-1)
        adapter.setProjects(projects)
        emptyView.setVisibility(projects.isEmpty())
        projectsList.setVisibility(projects.isNotEmpty())
        if (projects.isNotEmpty()) {
            if (atTop) projectsList.layoutManager?.scrollToPosition(0)
        }
    }

    private fun addNewProject() {
        AddProjectDialog.show(this)
    }

    private fun onNewProject(projectId: Long) {
        if (projectId > 0) {
            projectsList.layoutManager?.scrollToPosition(0)
            openProject(projectId)
        }
    }

    private fun openProject(projectId: Long) {
        val intent = EditorActivity.asDeepLinkIntent(projectId)
        intent.setPackage(requireContext().packageName)
        startActivity(intent)
    }

    override fun onProjectClick(project: Project) {
        openProject(project.id)
    }

    override fun onProjectMenuClick(anchor: View, project: Project) {
        val popup = PopupMenu(requireContext(), anchor)
        popup.inflate(R.menu.project)
        popup.setOnMenuItemClickListener { item ->
            when (item?.itemId) {
                R.id.action_change_name -> {
                    ChangeProjectNameDialog.show(this, project)
                    true
                }
                R.id.action_delete -> {
                    viewModel.deleteProject(project)
                    true
                }
                else -> false
            }
        }
        popup.show()
    }

    override fun onAddProject(project: Project) {
        viewModel.addNewProject(project)
    }

    override fun onProjectName(project: Project, name: String) {
        viewModel.updateProjectName(project, name)
    }
}