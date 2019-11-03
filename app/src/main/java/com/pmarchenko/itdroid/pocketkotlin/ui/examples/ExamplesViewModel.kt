package com.pmarchenko.itdroid.pocketkotlin.ui.examples

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.Example
import com.pmarchenko.itdroid.pocketkotlin.domain.repository.ProjectsRepository

/**
 * @author Pavel Marchenko
 */
class ExamplesViewModel(projectRepo: ProjectsRepository) : ViewModel() {

    val examples: LiveData<List<Example>> = projectRepo.examples
}