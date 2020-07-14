package com.pmarchenko.itdroid.pocketkotlin.ui.myprojects

import android.content.Context
import com.pmarchenko.itdroid.pocketkotlin.projects.ProjectsRepository
import com.pmarchenko.itdroid.pocketkotlin.projects.model.Project
import com.pmarchenko.itdroid.pocketkotlin.utils.usecase.UseCase

/**
 * @author Pavel Marchenko
 */
class ChangeProjectUseCase(
    context: Context,
    override val params: Project
) : UseCase<Project>() {

    private val repo = ProjectsRepository.newInstance(context)

    override suspend fun invoke(project: Project) {
        repo.updateProject(project)
    }
}