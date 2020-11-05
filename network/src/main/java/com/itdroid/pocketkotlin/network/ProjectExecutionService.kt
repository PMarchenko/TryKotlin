package com.itdroid.pocketkotlin.network

import com.itdroid.pocketkotlin.network.model.Project
import com.itdroid.pocketkotlin.network.model.ProjectExecutionResult
import retrofit2.Response
import retrofit2.http.*

/**
 * @author itdroid
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