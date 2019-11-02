package com.pmarchenko.itdroid.pocketkotlin.network

import com.pmarchenko.itdroid.pocketkotlin.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectExecutionResult
import retrofit2.Response

/**
 * @author Pavel Marchenko
 */
object DummyProjectExecutionService : ProjectExecutionService {

    override suspend fun execute(project: Project, type: String, runConf: String, filename: String, searchForMain: Boolean): Response<ProjectExecutionResult> {
        throw UnsupportedOperationException("Not supported in DummyProjectExecutionService")
    }
}