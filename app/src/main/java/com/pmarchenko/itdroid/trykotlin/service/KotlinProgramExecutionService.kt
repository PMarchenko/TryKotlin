package com.pmarchenko.itdroid.trykotlin.service

import com.pmarchenko.itdroid.trykotlin.model.ProjectExecutionResult
import com.pmarchenko.itdroid.trykotlin.model.Project
import retrofit2.Call
import retrofit2.http.*

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
interface KotlinProgramExecutionService {

    @FormUrlEncoded
    @Headers("content-type: application/x-www-form-urlencoded; charset=UTF-8")
    @POST("kotlinServer")
    fun execute(
        @Field("project") project: Project,

        @Query("type") type: String = project.type,
        @Query("runConf") runConf: String = project.runConfig,
        @Field("filename") filename: String = project.mainFileName,
        @Field("searchForMain") searchForMain: Boolean = project.searchForMain
    ): Call<ProjectExecutionResult>

}