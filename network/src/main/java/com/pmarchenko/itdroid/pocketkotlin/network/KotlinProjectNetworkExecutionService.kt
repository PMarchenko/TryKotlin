package com.pmarchenko.itdroid.pocketkotlin.network

import com.pmarchenko.itdroid.pocketkotlin.network.model.Project
import com.pmarchenko.itdroid.pocketkotlin.network.model.ProjectExecutionResult
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Pavel Marchenko
 */
object KotlinProjectNetworkExecutionService : ProjectExecutionService {

    private val service: ProjectExecutionService = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .also { builder -> StethoInjectorImpl.injectInto(builder) }
                .build()
        )
        .baseUrl("https://try.kotlinlang.org/")
        .addConverterFactory(ProjectConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProjectExecutionService::class.java)

    override suspend fun execute(
        type: String,
        runConf: String,
        project: Project,
        filename: String,
        searchForMain: Boolean
    ): Response<ProjectExecutionResult> =
        service.execute(type, runConf, project, filename, searchForMain)

}
