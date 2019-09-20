package com.pmarchenko.itdroid.pocketkotlin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.pmarchenko.itdroid.pocketkotlin.db.ProjectDao
import com.pmarchenko.itdroid.pocketkotlin.db.entity.ProjectWithFiles
import com.pmarchenko.itdroid.pocketkotlin.model.Error
import com.pmarchenko.itdroid.pocketkotlin.model.Loading
import com.pmarchenko.itdroid.pocketkotlin.model.Resource
import com.pmarchenko.itdroid.pocketkotlin.model.Success
import com.pmarchenko.itdroid.pocketkotlin.model.log.*
import com.pmarchenko.itdroid.pocketkotlin.model.project.Project
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectExecutionResult
import com.pmarchenko.itdroid.pocketkotlin.model.project.ProjectFile
import com.pmarchenko.itdroid.pocketkotlin.network.ProjectConverterFactory
import com.pmarchenko.itdroid.pocketkotlin.network.ProjectExecutionService
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
class ProjectsRepository(private val projectDao: ProjectDao) {

    val myProjects: LiveData<List<Project>> = Transformations.map(projectDao.getMyProjects()) { projectsWithFiles ->
        projectsWithFiles.map { projectWithFiles: ProjectWithFiles ->
            val project = projectWithFiles.project
            project.files.addAll(projectWithFiles.files)
            project
        }
    }

    fun addProject(project: Project) = projectDao.insert(project)

    fun deleteProject(project: Project) {
        projectDao.deleteProject(project)
    }

    fun loadProject(projectId: Long) = projectDao.getProject(projectId)

    fun updateFile(project: Project, file: ProjectFile, updateTimestamp: Boolean = true) {
        val timestamp = System.currentTimeMillis()
        projectDao.updateFile(
            if (updateTimestamp) {
                project.copy(dateModified = timestamp)
            } else {
                project
            },
            if (updateTimestamp) {
                file.copy(dateModified = timestamp)
            } else {
                file
            }
        )
    }

    fun updateProject(project: Project, updateTimestamp: Boolean = true) {
        projectDao.updateProject(
            if (updateTimestamp) {
                project.copy(dateModified = System.currentTimeMillis())
            } else {
                project
            }
        )
    }

    fun execute(log: LogLiveData, project: Project): LiveData<Resource<ProjectExecutionResult>> {
        val result = MutableLiveData<Resource<ProjectExecutionResult>>()

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
        val hasException = result.exception != null
        val hasError = result.hasErrors()
        val hasOutput = !result.text.isNullOrEmpty() || !(hasException || hasError)

        if (hasOutput) {
            log.postValue(InfoLogRecord(result.text ?: ""))
        }

        if (hasException) {
            log.postValue(ExceptionLogRecord(result.exception!!))
        }

        if (hasError) {
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

    companion object {

        val KOTLIN_SERVICE: ProjectExecutionService = Retrofit.Builder()
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
}