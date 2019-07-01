package com.pmarchenko.itdroid.trykotlin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.pmarchenko.itdroid.trykotlin.model.*
import com.pmarchenko.itdroid.trykotlin.service.KotlinProgramExecutionService
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Pavel Marchenko (Pavel.Marchenko@datart.com -- DataArt)
 */
class ProjectRepository {

    companion object {
        val KOTLIN_SERVICE: KotlinProgramExecutionService = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addNetworkInterceptor(StethoInterceptor())
                    .build()
            )
            .baseUrl("https://try.kotlinlang.org/")
            .addConverterFactory(ProjectConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KotlinProgramExecutionService::class.java)
    }

    fun execute(project: Project): LiveData<Resource<ProjectExecutionResult>> {
        val result = MutableLiveData<Resource<ProjectExecutionResult>>()
        result.postValue(Loading())
        KOTLIN_SERVICE.execute(project = project).enqueue(object : Callback<ProjectExecutionResult> {

            override fun onFailure(call: Call<ProjectExecutionResult>, t: Throwable) {
                result.postValue(Error(t.message ?: ""))
            }

            override fun onResponse(call: Call<ProjectExecutionResult>, response: Response<ProjectExecutionResult>) {
                if (response.isSuccessful) {
                    val executionResult = response.body()
                    if (executionResult == null) {
                        result.postValue(Error(""))
                    } else {
                        result.postValue(Success(executionResult))
                    }
                } else {
                    result.postValue(Error(response.errorBody()?.string() ?: "error"))
                }
            }
        })

        return result
    }
}

