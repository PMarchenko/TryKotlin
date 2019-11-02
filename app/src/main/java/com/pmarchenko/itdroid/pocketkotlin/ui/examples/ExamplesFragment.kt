package com.pmarchenko.itdroid.pocketkotlin.ui.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.db.AppDatabase
import com.pmarchenko.itdroid.pocketkotlin.db.entity.Example
import com.pmarchenko.itdroid.pocketkotlin.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.extentions.setVisibility
import com.pmarchenko.itdroid.pocketkotlin.network.DummyProjectExecutionService
import com.pmarchenko.itdroid.pocketkotlin.repository.ProjectsRepository
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorActivity
import com.pmarchenko.itdroid.pocketkotlin.ui.examples.adapter.ExamplesAdapter
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.DividerDecoration

/**
 * @author Pavel Marchenko
 */
class ExamplesFragment : Fragment() {

    private lateinit var viewModel: ExamplesViewModel
    private val viewModelProvider = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ExamplesViewModel::class.java)) {
                val projectDao = AppDatabase.getDatabase(requireActivity().applicationContext).getProjectDao()
                val executionService = DummyProjectExecutionService
                val projectRepo = ProjectsRepository(projectDao, executionService)
                @Suppress("UNCHECKED_CAST")
                return ExamplesViewModel(projectRepo) as T
            }
            error("Cannot create viewModel for $modelClass")
        }
    }

    private lateinit var progressView: View
    private lateinit var examplesList: RecyclerView

    private lateinit var adapter: ExamplesAdapter

    private val projectCallback = object : ProjectCallbackAdapter() {

        override fun onProjectClick(project: Project) {
            val intent = EditorActivity.asDeepLinkIntent(project.id)
            intent.setPackage(requireContext().packageName)
            startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProviders.of(this, viewModelProvider).get(ExamplesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_examples, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initUI()
        viewModel.examples.observe(viewLifecycleOwner, Observer { examples -> onExamples(examples ?: emptyList()) })
    }

    private fun initUI() {
        progressView = view?.findViewById(R.id.progress) ?: error("Cannot find progress view")
        examplesList = view?.findViewById(R.id.examplesList) ?: error("Cannot find projects list")

        examplesList.layoutManager = LinearLayoutManager(context)
        adapter = ExamplesAdapter(projectCallback)
        examplesList.adapter = adapter
        examplesList.addItemDecoration(DividerDecoration(requireContext()))

        progressView.setVisibility(true)
    }

    private fun onExamples(examples: List<Example>) {
        progressView.setVisibility(false)
        adapter.setExamples(examples)
    }
}