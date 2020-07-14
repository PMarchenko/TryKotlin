package com.pmarchenko.itdroid.pocketkotlin.ui.examples

import android.app.Application
import androidx.lifecycle.*
import com.pmarchenko.itdroid.pocketkotlin.projects.ProjectsRepository

/**
 * @author Pavel Marchenko
 */
class ExamplesViewModel(val app: Application) : AndroidViewModel(app) {

    private val projectRepo = ProjectsRepository.newInstance(app)

    private val _viewState = projectRepo.examples
        .map {
            ExamplesViewState(examples = it)
        } as MutableLiveData<ExamplesViewState>

    val viewState: LiveData<ExamplesViewState> by lazy {
        _viewState.value = ExamplesViewState(isLoading = true)
        _viewState
    }
}