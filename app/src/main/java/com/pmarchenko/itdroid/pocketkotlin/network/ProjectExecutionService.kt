package com.pmarchenko.itdroid.pocketkotlin.network

import com.pmarchenko.itdroid.pocketkotlin.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectExecutionResult
import retrofit2.Response
import retrofit2.http.*

/**
 * @author Pavel Marchenko
 */
interface ProjectExecutionService {

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded; charset=UTF-8")
    @POST("kotlinServer")
    suspend fun execute(
        @Field("project") project: Project,

        @Query("type") type: String = project.executionType,
        @Query("runConf") runConf: String = project.runConfig,
        @Field("filename") filename: String = project.mainFile,
        @Field("searchForMain") searchForMain: Boolean = project.searchForMain
    ): Response<ProjectExecutionResult>

}