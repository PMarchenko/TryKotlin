package com.pmarchenko.itdroid.pocketkotlin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.pmarchenko.itdroid.pocketkotlin.model.*
import com.pmarchenko.itdroid.pocketkotlin.model.log.*
import com.pmarchenko.itdroid.pocketkotlin.model.project.KotlinProject
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectExecutionResult
import com.pmarchenko.itdroid.pocketkotlin.service.KotlinProgramExecutionService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Pavel Marchenko
 */
class KotlinProjectRepository {

    companion object {

        val KOTLIN_SERVICE: KotlinProgramExecutionService = Retrofit.Builder()
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
            .create(KotlinProgramExecutionService::class.java)
    }

    fun execute(log: LogLiveData, project: KotlinProject): LiveData<Resource<ProjectExecutionResult>> {
        val result = MutableLiveData<Resource<ProjectExecutionResult>>()
        if (!project.isValid()) {
            log.postValue(ErrorLogRecord(ErrorLogRecord.ERROR_PROJECT))
            return result
        }

        result.postValue(Loading())
        log.postValue(RunLogRecord(project.name, project.args))
        KOTLIN_SERVICE.execute(project = project).enqueue(object : Callback<ProjectExecutionResult> {

            override fun onFailure(call: Call<ProjectExecutionResult>, t: Throwable) {
                onError(result, log, t.message)
            }

            override fun onResponse(call: Call<ProjectExecutionResult>, response: Response<ProjectExecutionResult>) {
                if (response.isSuccessful) {
                    val executionResult = response.body()
                    if (executionResult == null) {
                        onError(result, log, null)
                    } else {
                        onSuccess(result, log, executionResult)
                    }
                } else {
                    onError(result, log, response.errorBody()?.string())
                }
            }
        })
        return result
    }

    private fun onSuccess(
            out: MutableLiveData<Resource<ProjectExecutionResult>>,
            log: LogLiveData,
            result: ProjectExecutionResult
    ) {
        out.postValue(Success(result))
        if (result.exception != null) {
            log.postValue(ExceptionLogRecord(result.exception))
        } else if (result.text?.isNotEmpty() == true) {
            log.postValue(InfoLogRecord(result.text))
        }

        if (result.hasErrors()) {
            log.postValue(ErrorLogRecord(ErrorLogRecord.ERROR_PROJECT, errors = result.errors))
        }
    }

    private fun onError(
            result: MutableLiveData<Resource<ProjectExecutionResult>>,
            log: LogLiveData,
            errorMessage: String?,
            errorCode: Int = ErrorLogRecord.ERROR_MESSAGE
    ) {
        result.postValue(Error(errorMessage ?: ""))
        log.postValue(ErrorLogRecord(errorCode, errorMessage))
    }
}

