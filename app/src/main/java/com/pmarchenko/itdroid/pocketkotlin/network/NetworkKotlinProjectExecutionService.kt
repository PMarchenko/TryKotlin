package com.pmarchenko.itdroid.pocketkotlin.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.pmarchenko.itdroid.pocketkotlin.db.entity.Project
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectExecutionResult
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Pavel Marchenko
 */
object NetworkKotlinProjectExecutionService : ProjectExecutionService {

    override suspend fun execute(project: Project, type: String, runConf: String, filename: String, searchForMain: Boolean): Response<ProjectExecutionResult> {
        return KOTLIN_HTTP_SERVICE.execute(project, type, runConf, filename, searchForMain)
    }

    private val KOTLIN_HTTP_SERVICE: ProjectExecutionService = Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .addNetworkInterceptor(StethoInterceptor())
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build()
        )
        .baseUrl("https://try.kotlinlang.org/")
        .addConverterFactory(ProjectConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ProjectExecutionService::class.java)
}
