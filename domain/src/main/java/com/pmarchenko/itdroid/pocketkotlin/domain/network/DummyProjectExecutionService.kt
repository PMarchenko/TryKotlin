package com.pmarchenko.itdroid.pocketkotlin.domain.network

import com.pmarchenko.itdroid.pocketkotlin.domain.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.data.model.project.ProjectExecutionResult
import retrofit2.Response

/**
 * @author Pavel Marchenko
 */
object DummyProjectExecutionService : ProjectExecutionService {

    override suspend fun execute(
        project: Project,
        type: String,
        runConf: String,
        filename: String,
        searchForMain: Boolean
    ): Response<ProjectExecutionResult> {
        throw UnsupportedOperationException("Not supported in DummyProjectExecutionService")
    }
}