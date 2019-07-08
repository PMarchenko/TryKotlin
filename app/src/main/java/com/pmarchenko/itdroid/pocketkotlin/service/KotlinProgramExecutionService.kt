package com.pmarchenko.itdroid.pocketkotlin.service

import com.pmarchenko.itdroid.pocketkotlin.model.ProjectExecutionResult
import com.pmarchenko.itdroid.pocketkotlin.model.KotlinProject
import retrofit2.Call
import retrofit2.http.*

/**
 * @author Pavel Marchenko
 */
interface KotlinProgramExecutionService {

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded; charset=UTF-8")
    @POST("kotlinServer")
    fun execute(
        @Field("project") project: KotlinProject,

        @Query("type") type: String = project.type,
        @Query("runConf") runConf: String = project.runConfig,
        @Field("filename") filename: String = project.mainFileName,
        @Field("searchForMain") searchForMain: Boolean = project.searchForMain
    ): Call<ProjectExecutionResult>

}