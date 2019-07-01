package com.pmarchenko.itdroid.trykotlin.editor


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.pmarchenko.itdroid.trykotlin.R
import com.pmarchenko.itdroid.trykotlin.extentions.findView
import com.pmarchenko.itdroid.trykotlin.extentions.setVisibility
import com.pmarchenko.itdroid.trykotlin.view.ViewStateHandler

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
class EditorFragment : Fragment(), ViewStateHandler<EditorViewState> {
    private lateinit var view: EditorView
    private lateinit var presenter: EditorPresenter

    private val codeEditor: EditText by findView(R.id.code_editor)
    private val executeCodeFab: View by findView(R.id.editor_fab)
    private val progressView: View by findView(R.id.progress)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_editor, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        codeEditor.setText("fun main(args: Array<String>) {\n    println(\"Hello, world!\")\n}")
        this.view = EditorView(viewLifecycleOwner.lifecycle, this)
        presenter = EditorPresenter(this.view, ViewModelProviders.of(this).get(EditorViewModel::class.java))

        executeCodeFab.setOnClickListener {
            presenter.project.setCode(codeEditor.text.toString())
            presenter.executeProject()
        }
    }

    override fun onNewViewState(state: EditorViewState) {
        progressView.setVisibility(state.progressVisibility)
        codeEditor.isEnabled = !state.progressVisibility
        executeCodeFab.isEnabled = !state.progressVisibility

        state.codeExecutionResult?.let {
            Toast.makeText(requireContext(), it.text, Toast.LENGTH_LONG).show()
        }

        if (!TextUtils.isEmpty(state.errorMessage)) {
            Toast.makeText(requireContext(), "ERROR: ${state.errorMessage}", Toast.LENGTH_LONG).show()
        }
    }
}
