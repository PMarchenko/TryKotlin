package com.pmarchenko.itdroid.pocketkotlin.ui.examples

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmarchenko.itdroid.pocketkotlin.R
import com.pmarchenko.itdroid.pocketkotlin.databinding.FragmentExamplesBinding
import com.pmarchenko.itdroid.pocketkotlin.ui.editor.EditorActivity
import com.pmarchenko.itdroid.pocketkotlin.ui.examples.adapter.ExamplesAdapter
import com.pmarchenko.itdroid.pocketkotlin.ui.myprojects.projectClickCallback
import com.pmarchenko.itdroid.pocketkotlin.ui.recycler.DividerDecoration

/**
 * @author Pavel Marchenko
 */
class ExamplesFragment : Fragment() {

    private lateinit var binding: FragmentExamplesBinding
    private lateinit var adapter: ExamplesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_examples, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentExamplesBinding.bind(view)
        initUi()

        ViewModelProviders.of(this).get(ExamplesViewModel::class.java)
            .viewState.observe(viewLifecycleOwner, Observer { updateUI(it) })
    }

    private fun initUi() {
        binding.examplesList.layoutManager = LinearLayoutManager(context)
        binding.examplesList.addItemDecoration(DividerDecoration(requireContext()))
        adapter = ExamplesAdapter(
            projectClickCallback { project ->
                val intent = EditorActivity.asDeepLinkIntent(project.id)
                intent.setPackage(requireContext().packageName)
                startActivity(intent)
            }
        ).also { binding.examplesList.adapter = it }
    }

    private fun updateUI(state: ExamplesViewState) {
        binding.progress.isVisible = state.isLoading
        adapter.setExamples(state.examples)
    }
}