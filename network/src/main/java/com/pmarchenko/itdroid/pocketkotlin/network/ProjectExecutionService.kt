package com.pmarchenko.itdroid.pocketkotlin.network

import com.pmarchenko.itdroid.pocketkotlin.network.model.Project
import com.pmarchenko.itdroid.pocketkotlin.network.model.ProjectExecutionResult
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
        @Query("type") type: String,
        @Query("runConf") runConf: String,
        @Field("project") project: Project,
        @Field("filename") filename: String,
        @Field("searchForMain") searchForMain: Boolean
    ): Response<ProjectExecutionResult>
}